package app.medview.domain.dto.users

import app.medview.domain.users.Doctor

data class PatientDto(
    val doctor: DoctorDto,
    val username: String,
    val email: String
)
