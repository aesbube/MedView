package app.medview.web

import app.medview.domain.Appointment
import app.medview.domain.dto.AppointmentDto
import app.medview.service.AppointmentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/appointments")
class AppointmentController(private val appointmentService: AppointmentService) {
    @GetMapping
    fun getAllAppointments() = appointmentService.getAllAppointments()

    @GetMapping("/patient/{patientId}")
    fun getAppointmentsByPatientId(@PathVariable patientId: Long): ResponseEntity<List<Appointment>> {
        val appointments = appointmentService.getAppointmentsByPatientId(patientId)
        return if (appointments.isNotEmpty()) {
            ResponseEntity.ok(appointments)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{appointmentId}")
    fun getAppointmentsById(@PathVariable ("appointmentId") appointmentId: Long): ResponseEntity<AppointmentDto> {
        val appointment = appointmentService.getAppointmentById(appointmentId)
        return ResponseEntity(appointment, HttpStatus.OK)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteAppointment(@PathVariable id: Long): ResponseEntity<String> {
        val response = appointmentService.deleteAppointment(id)
        return ResponseEntity.ok(response.message)
    }
}