package app.medview.domain.dto.users

data class PatientDto(
    val doctor: DoctorDto,
    val username: String,
    val email: String
)
