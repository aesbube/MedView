package app.medview.service

import app.medview.domain.Schedule
import app.medview.domain.dto.FreeAppointmentDto
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.ScheduleDto

interface ScheduleService {
    fun getAllSchedules(): List<Schedule>
    fun getScheduleById(id: Long): Schedule
    fun createSchedule(scheduleDto: ScheduleDto): MessageResponse
    fun updateSchedule(id: Long, scheduleDto: ScheduleDto): MessageResponse
    fun deleteSchedule(id: Long): MessageResponse
}