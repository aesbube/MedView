package app.medview.domain.converter

import app.medview.domain.Schedule
import app.medview.domain.dto.ScheduleDto
import org.springframework.stereotype.Component

@Component
class ScheduleEntityToDtoConverter(private val specialistConverter: SpecialistEntityToDtoConverter) {
    fun convert(schedule: Schedule) = schedule.specialist?.let { specialistConverter.convert(it) }?.let {
        ScheduleDto(
            id = schedule.id,
            specialist = it
        )
    }
}