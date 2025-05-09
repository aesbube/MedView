package app.medview.domain.dto.users

data class PatientDto(
    val id: Long,
    val doctor: DoctorDto,
    val username: String,
    val email: String
)
