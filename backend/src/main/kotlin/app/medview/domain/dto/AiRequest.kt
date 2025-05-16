package app.medview.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class AiRequest(
    val model: String,
    val messages: List<AiMessage>,
    val temperature: Double? = 0.7
)
