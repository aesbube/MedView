package app.medview.domain.dto

import app.medview.domain.PrescriptionStatus
import app.medview.domain.dto.users.DoctorDto
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.users.Doctor
import app.medview.domain.users.Patient
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class PrescriptionDto(
    val id: String?,
    val patient: PatientDto,
    val doctor: DoctorDto,
    val medicine: String?,
    val frequency: String?,
    var status: PrescriptionStatus = PrescriptionStatus.ACTIVE,
    val expirationDate: LocalDate? = LocalDate.now().plus(3, ChronoUnit.MONTHS),
)