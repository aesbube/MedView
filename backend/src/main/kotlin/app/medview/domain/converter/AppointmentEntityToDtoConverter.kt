package app.medview.domain.converter

import app.medview.domain.Appointment
import app.medview.domain.dto.AppointmentDto
import app.medview.domain.dto.users.PatientDto
import org.springframework.stereotype.Component

@Component
class AppointmentEntityToDtoConverter (
    private val patientConverter: PatientEntityToDtoConverter,
    private val doctorConverter: DoctorEntityToDtoConverter,
    private val scheduleConverter: ScheduleEntityToDtoConverter
) {
    fun convert(appointment: Appointment) = AppointmentDto(
        id = appointment.id,
        schedule = appointment.schedule?.let { scheduleConverter.convert(it) },
        patient = appointment.patient?.let { patientConverter.convert(it) },
        assignee = appointment.assignee?.let { doctorConverter.convert(it) },
        date = appointment.date,
        time = appointment.time,
        location = appointment.location,
        refNumber = appointment.refNumber,
        status = appointment.status
    )
}