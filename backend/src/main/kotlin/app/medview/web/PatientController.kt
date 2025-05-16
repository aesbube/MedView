package app.medview.web

import app.medview.domain.Prescription
import app.medview.domain.dto.AppointmentDto
import app.medview.domain.dto.DiagnosisDto
import app.medview.domain.dto.PrescriptionDto
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.dto.users.PatientRequestDto
import app.medview.domain.users.Patient
import app.medview.service.OpenAiService
import app.medview.service.users.PatientService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/patients")
class PatientController(
    private val patientService: PatientService,
    private val openAiService: OpenAiService
    ) {
    @PostMapping("/update")
    fun addDetailsToPatient(@RequestBody patientDto: PatientDto): ResponseEntity<PatientDto> {
        val updatedPatient = patientService.addDetailsToPatient(patientDto)
        return ResponseEntity.ok(updatedPatient)
    }

    @GetMapping("/me")
    fun getCurrentPatient(): ResponseEntity<PatientDto> {
        return ResponseEntity(patientService.getCurrentPatient(), HttpStatus.OK)
    }

    @GetMapping("/me/prescriptions")
    fun getPrescriptionsOfCurrentUser(): ResponseEntity<List<PrescriptionDto>> {
        return ResponseEntity(patientService.getPrescriptionsOfPatient(), HttpStatus.OK)
    }

    @GetMapping("/appointment/{refNumber}")
    fun getAppointmentByRefNumber(@PathVariable refNumber: String): ResponseEntity<AppointmentDto> {
        val appointment = patientService.getAppointmentOfPatient(refNumber)
        return ResponseEntity.ok(appointment)
    }

    @GetMapping("/appointments")
    fun getAllAppointments(): ResponseEntity<List<AppointmentDto>> {
        val appointments = patientService.getAllAppointmentsOfPatient()
        return ResponseEntity.ok(appointments)
    }

    @GetMapping("/appointment/{refNumber}/diagnosis/simplify")
    fun simplifyDiagnosis(@PathVariable refNumber: String): ResponseEntity<DiagnosisDto> {
        val simplifiedDiagnosis = openAiService.simplify(refNumber)
        return ResponseEntity(simplifiedDiagnosis, HttpStatus.OK)
    }
}