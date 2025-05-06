package app.medview.web

import app.medview.domain.Schedule
import app.medview.domain.dto.AppointmentDto
import app.medview.domain.dto.DiagnosisDto
import app.medview.domain.dto.FreeAppointmentDto
import app.medview.domain.dto.ScheduleDto
import app.medview.domain.dto.users.SpecialistDto
import app.medview.domain.users.Specialist
import app.medview.service.ScheduleService
import app.medview.service.users.SpecialistService
import org.slf4j.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/specialists")
class SpecialistController(
    private val specialistService: SpecialistService,
) {
    @GetMapping
    fun getAllSpecialists(): ResponseEntity<List<Specialist>> {
        val specialists = specialistService.getAllSpecialists()
        return ResponseEntity.ok(specialists)
    }

    @GetMapping("/search")
    fun getSpecialistsByUsername(
        @RequestParam(
            "name", required = true
        ) username: String
    ): ResponseEntity<List<Specialist>> {
        val specialists = specialistService.getSpecialistsByUsername(username)
        return ResponseEntity.ok(specialists)
    }

    @PostMapping("/update")
    fun addDetailsToSpecialist(@RequestBody specialistDto: SpecialistDto): ResponseEntity<String> {
        val response = specialistService.addDetailsToSpecialist(specialistDto)
        return ResponseEntity.ok(response.message)
    }

    @GetMapping("/schedule")
    fun getScheduleBySpecialistId(specialistId: Long): ResponseEntity<Schedule> {
        val schedule = specialistService.getSpecialistScheduleById(specialistId)
        return ResponseEntity.ok(schedule)
    }

    @PostMapping("/appointments")
    fun setFreeAppointments(@RequestBody appointments: List<FreeAppointmentDto>): ResponseEntity<String> {
        val response = specialistService.setFreeAppointments(appointments)
        return ResponseEntity.ok(response.message)
    }

    @PostMapping("/appointments/{appointmentId}/diagnosis")
    fun writeDiagnosis(
        @PathVariable(value = "appointmentId") appointmentId: Long,
        @RequestBody diagnosisDto: DiagnosisDto
    ): ResponseEntity<String> {
        val response = specialistService.writeDiagnosis(appointmentId, diagnosisDto)
        return ResponseEntity.ok(response.message)
    }
}