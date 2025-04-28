package app.medview.domain.dto

import java.util.*

data class AppointmentDto(
    val scheduleId: Long,
    val patientId: Long,
    val date: Date,
    val time: String,
    val location: String,
)
