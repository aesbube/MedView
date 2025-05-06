package app.medview.domain.dto

import java.time.LocalDate

data class OccupyAppointmentDto(
    val scheduleId: Long,
    val patientId: Long,
    val refNumber: String
)