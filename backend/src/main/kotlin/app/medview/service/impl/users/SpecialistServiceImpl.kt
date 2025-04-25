package app.medview.service.impl.users

import app.medview.domain.Role
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.users.SpecialistDto
import app.medview.domain.users.Specialist
import app.medview.repository.SpecialistRepository
import app.medview.repository.UserRepository
import app.medview.service.users.SpecialistService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SpecialistServiceImpl(
    private val specialistRepository: SpecialistRepository,
    private val userRepository: UserRepository
) : SpecialistService {
    override fun getAllSpecialists(): List<Specialist> {
        return specialistRepository.findAll()
    }

    override fun getSpecialistById(id: Long): Specialist {
        return specialistRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Specialist not found with id: $id")
        }
    }

    override fun addDetailsToSpecialist(specialistDto: SpecialistDto): MessageResponse {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name

        val user = userRepository.findByUsername(username)
            ?: throw RuntimeException("Specialist not found with username: $username")

        if (user.role != Role.SPECIALIST) {
            throw RuntimeException("User is not a specialist")
        }

        val specialist = Specialist(
            id = user.id,
            username = user.username,
            password = user.password,
            email = user.email,
            specialty = specialistDto.specialty,
            licenseNumber = specialistDto.licenseNumber,
            yearsOfExperience = specialistDto.yearsOfExperience,
        )

        specialistRepository.save(specialist)
        return MessageResponse("Specialist details added successfully")
    }
}