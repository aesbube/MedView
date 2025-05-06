package app.medview.service.impl.users

import app.medview.domain.Role
import app.medview.domain.Schedule
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.ScheduleDto
import app.medview.domain.dto.users.SpecialistDto
import app.medview.domain.users.Specialist
import app.medview.repository.ScheduleRepository
import app.medview.repository.SpecialistRepository
import app.medview.service.ScheduleService
import app.medview.service.users.SpecialistService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SpecialistServiceImpl(
    private val specialistRepository: SpecialistRepository,
    private val scheduleRepository: ScheduleRepository,
) : SpecialistService {
    override fun getAllSpecialists(): List<Specialist> {
        return specialistRepository.findAll()
    }

    override fun getSpecialistsByUsername(username: String): List<Specialist> {
        return specialistRepository.findByUsernameContainingIgnoreCase(username)
    }

    override fun getSpecialistById(id: Long): Specialist {
        return specialistRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Specialist not found with id: $id")
        }
    }

    override fun getSpecialistScheduleById(id: Long): ScheduleDto {
        val schedule = scheduleRepository.findBySpecialistId(id)
        return ScheduleDto(
            specialistId = id
        )
    }

    override fun addDetailsToSpecialist(specialistDto: SpecialistDto): MessageResponse {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name

        val specialist = specialistRepository.findByUsername(username)
            ?: throw RuntimeException("Specialist not found with username: $username")

        if (specialist.role != Role.SPECIALIST) {
            throw RuntimeException("User is not a specialist")
        }

        specialist.specialty = specialistDto.specialty
        specialist.licenseNumber = specialistDto.licenseNumber
        specialist.yearsOfExperience = specialistDto.yearsOfExperience

        specialistRepository.save(specialist)
        return MessageResponse("Specialist details added successfully")
    }
}