package app.medview.domain.dto.users

import jakarta.validation.constraints.Email

data class PharmacistDto(
    val username: String,
    val email: String,
    val licenseNumber: Int,
    val pharmacyName: String,
    val pharmacyAddress: String
)
