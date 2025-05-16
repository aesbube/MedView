package app.medview.service.impl

import app.medview.config.OpenAiConfig
import app.medview.domain.converter.DiagnosisEntityToDtoConverter
import app.medview.service.OpenAiService
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import app.medview.domain.dto.AiMessage
import app.medview.domain.dto.AiRequest
import app.medview.domain.dto.AiResponse
import app.medview.domain.dto.DiagnosisDto
import app.medview.repository.AppointmentRepository
import app.medview.repository.DiagnosisRepository
import kotlinx.serialization.encodeToString
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.concurrent.TaskRunner.Companion.logger

@Service
class OpenAiServiceImpl(
    private final val openAiConfig: OpenAiConfig,
    val diagnosisRepository: DiagnosisRepository,
    val appointmentRepository: AppointmentRepository,
    val diagnosisConverter: DiagnosisEntityToDtoConverter
) : OpenAiService {

    private val apiKey = openAiConfig.apiKey
    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    override fun simplify(refNumber: String): DiagnosisDto? {

        val appointment = appointmentRepository.findByRefNumber(refNumber) ?: return null
        val diagnosis = appointment.let { diagnosisRepository.findDiagnosisByAppointmentId(it.id) } ?: return null

        if (diagnosis.simplification!= null)
            if (diagnosis.simplification.length>5)
                return diagnosisConverter.convert(diagnosis)

        val prompt =

                    "{\n" +
                    "name: ${diagnosis.name},\n" +
                    "description: ${diagnosis.description},\n" +
                    "treatment: ${diagnosis.treatment}\n" +
                    "}\n" +
                            "Explain this diagnosis to me like I am 5 years old. Maximum explanation character limit: 1000 characters"

        val aiRequest = AiRequest(
            model = "gpt-3.5-turbo-0125",
            messages = listOf(AiMessage(role = "user", content = prompt))
        )

        val requestBody = json.encodeToString(aiRequest)

        val body = requestBody.toRequestBody("application/json".toMediaType())


        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions") // Correct Endpoint
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(body)
            .build()

        logger.info("Request Body: $requestBody") // Log the request body for debugging

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                val errorBody = response.body?.string()
//                logger.error("Request failed with code: ${response.code}, body: $errorBody")
                throw Exception("Failed: ${response.code} - $errorBody")
            }

            val responseBody = response.body?.string()
            logger.info("Response Body: $responseBody") // Log the response body
            val aiResponse = json.decodeFromString<AiResponse>(responseBody!!)
            val simplification = aiResponse.choices.first().message.content
            val updatedDiagnosis = diagnosis.copy(
                    id = diagnosis.id,
                    name = diagnosis.name,
                    description = diagnosis.description,
                    treatment = diagnosis.treatment,
                    simplification = simplification
                )

            diagnosisRepository.save(updatedDiagnosis)
            return diagnosisConverter.convert(updatedDiagnosis)
        }
    }

}
