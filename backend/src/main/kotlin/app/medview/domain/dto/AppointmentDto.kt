package app.medview.domain.dto

import app.medview.domain.AppointmentStatus
import java.time.LocalDate

data class AppointmentDto(
    val scheduleId: Long?,
    val patientId: Long?,
    val assigneeId: Long?,
    val date: LocalDate?,
    val time: String,
    val location: String,
    val refNumber: String?,
    val status: AppointmentStatus
)
