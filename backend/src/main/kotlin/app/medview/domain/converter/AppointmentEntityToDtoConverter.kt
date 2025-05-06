package app.medview.domain.converter

import app.medview.domain.Appointment
import app.medview.domain.dto.AppointmentDto
import org.springframework.stereotype.Component

@Component
class AppointmentEntityToDtoConverter {
    fun convert(appointment: Appointment) = AppointmentDto(
        scheduleId = appointment.schedule!!.id,
        patientId = appointment.patient!!.id,
        assigneeId = appointment.assignee!!.id,
        date = appointment.date,
        time = appointment.time,
        location = appointment.location,
        refNumber = appointment.refNumber,
        status = appointment.status
    )
}