package app.medview.domain.dto.users

data class DoctorDto(
    val specialty: String,
    val licenseNumber: String,
    val yearsOfExperience: Int,
    val hospitalName: String,
)
