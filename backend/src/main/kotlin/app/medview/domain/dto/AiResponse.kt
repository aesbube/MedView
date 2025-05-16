package app.medview.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class AiResponse(
    val choices: List<AiChoice>
)