package app.medview.web

import app.medview.domain.dto.AppointmentDto
import app.medview.domain.dto.FreeAppointmentDto
import app.medview.domain.dto.ScheduleDto
import app.medview.domain.dto.users.SpecialistDto
import app.medview.domain.users.Specialist
import app.medview.service.ScheduleService
import app.medview.service.users.SpecialistService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/specialists")
class SpecialistController(
    private val specialistService: SpecialistService,
    private val scheduleService: ScheduleService
) {
    @GetMapping
    fun getAllSpecialists(): ResponseEntity<List<Specialist>> {
        val specialists = specialistService.getAllSpecialists()
        return ResponseEntity.ok(specialists)
    }

    @PostMapping("/update")
    fun addDetailsToSpecialist(@RequestBody specialistDto: SpecialistDto): ResponseEntity<String> {
        val response = specialistService.addDetailsToSpecialist(specialistDto)
        return ResponseEntity.ok(response.message)
    }

    @GetMapping("/schedule")
    fun getScheduleBySpecialistId(specialistId: Long): ResponseEntity<ScheduleDto> {
        val schedule = specialistService.getSpecialistScheduleById(specialistId)
        return ResponseEntity.ok(schedule)
    }

    @PostMapping("/appointments")
    fun setFreeAppointments(@RequestBody appointments: List<FreeAppointmentDto>): ResponseEntity<String> {
        val response = scheduleService.setFreeAppointments(appointments)
        return ResponseEntity.ok(response.message)
    }
}