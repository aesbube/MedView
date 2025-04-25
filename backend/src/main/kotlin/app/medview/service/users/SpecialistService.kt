package app.medview.service.users

import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.users.SpecialistDto
import app.medview.domain.users.Specialist

interface SpecialistService {
    fun getAllSpecialists(): List<Specialist>
    fun getSpecialistById(id: Long): Specialist
    fun addDetailsToSpecialist(specialistDto: SpecialistDto): MessageResponse
}