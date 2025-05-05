package app.medview.domain.dto.users

data class DoctorUpdateRequestDto (
    val username: String?,
    val email: String?,
    val specialty: String?,
    val licenseNumber: String?,
    val yearsOfExperience: Int?,
    val hospitalName: String?,
)