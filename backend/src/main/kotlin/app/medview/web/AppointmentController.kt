package app.medview.web

import app.medview.domain.Appointment
import app.medview.domain.dto.AppointmentDto
import app.medview.service.AppointmentService
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

    @GetMapping("/schedule/{scheduleId}")
    fun getAppointmentsByScheduleId(@PathVariable scheduleId: Long): ResponseEntity<List<Appointment>> {
        val appointments = appointmentService.getAppointmentsByScheduleId(scheduleId)
        return if (appointments.isNotEmpty()) {
            ResponseEntity.ok(appointments)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/create")
    fun createAppointment(@RequestBody appointmentDto: AppointmentDto): ResponseEntity<String> {
        val response = appointmentService.createAppointment(appointmentDto)
        return ResponseEntity.ok(response.message)
    }

    @PostMapping("/update/{id}")
    fun updateAppointment(@PathVariable id: Long, @RequestBody appointmentDto: AppointmentDto): ResponseEntity<String> {
        val response = appointmentService.updateAppointment(id, appointmentDto)
        return ResponseEntity.ok(response.message)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteAppointment(@PathVariable id: Long): ResponseEntity<String> {
        val response = appointmentService.deleteAppointment(id)
        return ResponseEntity.ok(response.message)
    }
}