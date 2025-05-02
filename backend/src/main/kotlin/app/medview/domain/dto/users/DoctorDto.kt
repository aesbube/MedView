package app.medview.domain.dto.users

import jakarta.validation.constraints.Email

data class DoctorDto(
    val username: String,
    val email: String,
    val specialty: String,
    val licenseNumber: String,
    val yearsOfExperience: Int,
    val hospitalName: String,
)
