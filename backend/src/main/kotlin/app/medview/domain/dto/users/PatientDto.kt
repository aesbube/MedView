package app.medview.domain.dto.users

import app.medview.domain.users.Doctor

data class PatientDto(
    val doctorId: Long?,
    val username: String,
    val email: String
)
