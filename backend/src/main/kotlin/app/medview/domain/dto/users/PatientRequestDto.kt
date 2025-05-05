package app.medview.domain.dto.users

class PatientRequestDto (
    val doctor: DoctorDto?,
    val username: String?,
    val email: String?
)