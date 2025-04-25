package app.medview.service.impl.users

import app.medview.domain.Role
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.users.DoctorDto
import app.medview.domain.users.Doctor
import app.medview.repository.DoctorRepository
import app.medview.repository.UserRepository
import app.medview.service.users.DoctorService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class DoctorServiceImpl(private val doctorRepository: DoctorRepository, private val userRepository: UserRepository) :
    DoctorService {
        val logger = org.slf4j.LoggerFactory.getLogger(DoctorServiceImpl::class.java)
    override fun getAllDoctors(): List<Doctor> {
        return doctorRepository.findAll()
    }

    override fun getDoctorById(id: Long): Doctor {
        return doctorRepository.findById(id).orElseThrow {
            throw RuntimeException("Doctor not found with id: $id")
        }
    }

    override fun addDetailsToDoctor(doctorDto: DoctorDto): MessageResponse {
        logger.info("Adding doctor details")
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name

        logger.info("Adding doctor details for user: $username")
        logger.info("Doctor DTO: $doctorDto")

        val user = userRepository.findByUsername(username)
            ?: throw RuntimeException("Doctor not found with username: $username")

        if (user.role != Role.DOCTOR) {
            throw RuntimeException("User is not a doctor")
        }

        val doctor = Doctor(
            id = user.id,
            username = user.username,
            password = user.password,
            email = user.email,
            specialty = doctorDto.specialty,
            licenseNumber = doctorDto.licenseNumber,
            yearsOfExperience = doctorDto.yearsOfExperience,
            hospitalName = doctorDto.hospitalName
        )

        doctorRepository.save(doctor)
        return MessageResponse("Doctor details added successfully")
    }
}