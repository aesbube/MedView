package app.medview.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class AiChoice(
    val message: AiMessage
)

