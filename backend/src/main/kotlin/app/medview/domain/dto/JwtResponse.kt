package app.medview.domain.dto

data class JwtResponse(
    val token: String,
    val type: String = "Bearer",
    val id: Long,
    val username: String
)