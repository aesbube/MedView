package app.medview.domain.dto.users

data class PharmacistDto(
    val pharmacyName: String,
    val pharmacyAddress: String,
    val licenseNumber: Int,
)
