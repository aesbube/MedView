package app.medview.domain.dto.users

data class SpecialistDto(
    val username: String,
    val email: String,
    val specialty: String,
    val licenseNumber: String,
    val yearsOfExperience: Int,
)
