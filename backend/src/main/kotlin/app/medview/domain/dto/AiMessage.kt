package app.medview.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class AiMessage(
    val role: String,
    val content: String
)
