package app.medview.domain.dto

import app.medview.domain.AppointmentStatus
import app.medview.domain.dto.users.DoctorDto
import app.medview.domain.dto.users.PatientDto
import java.time.LocalDate

data class AppointmentDto(
    val id: Long,
    val schedule: ScheduleDto?,
    val patient: PatientDto?,
    val assignee: DoctorDto?,
    val date: LocalDate?,
    val time: String,
    val location: String,
    val refNumber: String?,
    val status: AppointmentStatus
)
