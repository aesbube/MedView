package app.medview.domain.dto

data class ScheduleDto(
    val scheduleId: Long,
    val specialistId: Long,
    val occupiedAppointments: List<AppointmentDto>,
)
