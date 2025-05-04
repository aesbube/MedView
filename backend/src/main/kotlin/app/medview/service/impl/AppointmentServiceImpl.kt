package app.medview.service.impl

import app.medview.domain.Appointment
import app.medview.domain.dto.AppointmentDto
import app.medview.domain.dto.MessageResponse
import app.medview.repository.AppointmentRepository
import app.medview.service.AppointmentService
import app.medview.service.ScheduleService
import app.medview.service.UserService
import app.medview.service.users.PatientService
import org.springframework.stereotype.Service

@Service
class AppointmentServiceImpl(
        private val appointmentRepository: AppointmentRepository,
        private val patientService: PatientService,
        private val scheduleService: ScheduleService,
        private val userService: UserService)
    : AppointmentService {
    override fun getAllAppointments(): List<Appointment> {
        return appointmentRepository.findAll()
    }

    override fun getAppointmentsByPatientId(patientId: Long): List<Appointment> {
        return appointmentRepository.findByPatientId(patientId)
    }

    override fun getAppointmentsByScheduleId(scheduleId: Long): List<Appointment> {
        return appointmentRepository.findByScheduleId(scheduleId)
    }

    override fun createAppointment(appointmentDto: AppointmentDto): MessageResponse {
        val patient = patientService.getPatientEntityById(appointmentDto.patientId)
        val schedule = scheduleService.getScheduleById(appointmentDto.scheduleId)
        val assignee = userService.getCurrentUser()
        val appointment = Appointment(
            schedule = schedule,
            patient = patient,
            date = appointmentDto.date,
            time = appointmentDto.time,
            location = appointmentDto.location,
            assignee = assignee,
        )

        appointmentRepository.save(appointment)
        return MessageResponse("Appointment created successfully")
    }

    override fun updateAppointment(id: Long, appointmentDto: AppointmentDto): MessageResponse {
        val existingAppointment = appointmentRepository.findById(id).orElseThrow { Exception("Appointment not found") }
        val patient = patientService.getPatientEntityById(appointmentDto.patientId)
        val schedule = scheduleService.getScheduleById(appointmentDto.scheduleId)
        existingAppointment.apply {
            this.schedule = schedule
            this.patient = patient
            this.date = appointmentDto.date
            this.time = appointmentDto.time
            this.location = appointmentDto.location
        }

        appointmentRepository.save(existingAppointment)
        return MessageResponse("Appointment updated successfully")
    }

    override fun deleteAppointment(id: Long): MessageResponse {
        val appointment = appointmentRepository.findById(id).orElseThrow { Exception("Appointment not found") }
        appointmentRepository.delete(appointment)
        return MessageResponse("Appointment deleted successfully")
    }
}