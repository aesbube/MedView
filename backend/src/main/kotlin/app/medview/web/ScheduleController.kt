package app.medview.web

import app.medview.domain.Schedule
import app.medview.domain.dto.ScheduleDto
import app.medview.service.ScheduleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/schedules")
class ScheduleController(private val scheduleService: ScheduleService) {
    @GetMapping
    fun getAllSchedules(): ResponseEntity<List<Schedule>> {
        val schedules = scheduleService.getAllSchedules()
        return if (schedules.isNotEmpty()) {
            ResponseEntity.ok(schedules)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{id}")
    fun getScheduleById(@PathVariable id: Long): ResponseEntity<Schedule> {
        val schedule = scheduleService.getScheduleById(id)
        return ResponseEntity.ok(schedule)
    }

    @PostMapping("/create")
    fun createSchedule(@RequestBody scheduleDto: ScheduleDto): ResponseEntity<String> {
        val response = scheduleService.createSchedule(scheduleDto)
        return ResponseEntity.ok(response.message)
    }

    @PostMapping("/update/{id}")
    fun updateSchedule(@PathVariable id: Long, @RequestBody scheduleDto: ScheduleDto): ResponseEntity<String> {
        val response = scheduleService.updateSchedule(id, scheduleDto)
        return ResponseEntity.ok(response.message)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteSchedule(@PathVariable id: Long): ResponseEntity<String> {
        val response = scheduleService.deleteSchedule(id)
        return ResponseEntity.ok(response.message)
    }
}