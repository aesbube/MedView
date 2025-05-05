package app.medview.service

import app.medview.domain.Appointment
import app.medview.domain.dto.AppointmentDto
import app.medview.domain.dto.AppointmentRequestDto
import app.medview.domain.dto.MessageResponse
import app.medview.domain.users.Doctor

interface AppointmentService {
    fun getAllAppointments(): List<Appointment>
    fun getAppointmentsByPatientId(patientId: Long): List<Appointment>
    fun getAppointmentsBySpecialistId(specialistId: Long): List<Appointment>
    fun getAppointmentsByScheduleId(scheduleId: Long): List<Appointment>
    fun createAppointment(patientId: Long, assignee: Doctor, appointmentRequestDto: AppointmentRequestDto): MessageResponse
    fun updateAppointment(id: Long, appointmentDto: AppointmentDto): MessageResponse
    fun deleteAppointment(id: Long): MessageResponse
}