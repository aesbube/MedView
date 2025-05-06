package app.medview.service.impl

import app.medview.domain.Schedule
import app.medview.domain.dto.*
import app.medview.repository.ScheduleRepository
import app.medview.service.AppointmentService
import app.medview.service.ScheduleService
import app.medview.service.users.SpecialistService
import org.springframework.stereotype.Service

@Service
class ScheduleServiceImpl(
    private val scheduleRepository: ScheduleRepository,
    private val specialistService: SpecialistService
) : ScheduleService {
    override fun getAllSchedules(): List<Schedule> {
        return scheduleRepository.findAll()
    }

    override fun getScheduleById(id: Long): Schedule {
        return scheduleRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Schedule with id $id not found")
        }
    }

    override fun createSchedule(scheduleDto: ScheduleDto): MessageResponse {
        val specialist = specialistService.getSpecialistById(scheduleDto.specialistId)
        val schedule = Schedule(specialist = specialist)

        scheduleRepository.save(schedule)
        return MessageResponse("Schedule created successfully")
    }

    override fun updateSchedule(id: Long, scheduleDto: ScheduleDto): MessageResponse {
        val existingSchedule = scheduleRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Schedule with id $id not found")
        }
        val specialist = specialistService.getSpecialistById(scheduleDto.specialistId)
        existingSchedule.apply {
            this.specialist = specialist
        }

        scheduleRepository.save(existingSchedule)
        return MessageResponse("Schedule updated successfully")
    }

    override fun deleteSchedule(id: Long): MessageResponse {
        val schedule = scheduleRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Schedule with id $id not found")
        }
        scheduleRepository.delete(schedule)
        return MessageResponse("Schedule deleted successfully")
    }

}