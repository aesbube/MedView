package app.medview.service

import app.medview.domain.Appointment
import app.medview.domain.dto.AppointmentDto
import app.medview.domain.dto.MessageResponse

interface AppointmentService {
    fun getAllAppointments(): List<Appointment>
    fun getAppointmentsByPatientId(patientId: Long): List<Appointment>
    fun getAppointmentsByScheduleId(scheduleId: Long): List<Appointment>
    fun createAppointment(appointmentDto: AppointmentDto): MessageResponse
    fun updateAppointment(id: Long, appointmentDto: AppointmentDto): MessageResponse
    fun deleteAppointment(id: Long)
}