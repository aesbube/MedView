package app.medview.service.users

import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.ScheduleDto
import app.medview.domain.dto.users.SpecialistDto
import app.medview.domain.users.Specialist

interface SpecialistService {
    fun getAllSpecialists(): List<Specialist>
    fun getSpecialistById(id: Long): Specialist
    fun getSpecialistScheduleById(id: Long): ScheduleDto
    fun addDetailsToSpecialist(specialistDto: SpecialistDto): MessageResponse
}