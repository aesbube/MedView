package app.medview.service.impl.users

import app.medview.domain.Role
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.users.DoctorDto
import app.medview.domain.users.Doctor
import app.medview.repository.DoctorRepository
import app.medview.service.users.DoctorService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class DoctorServiceImpl(private val doctorRepository: DoctorRepository) : DoctorService {
    override fun getAllDoctors(): List<Doctor> {
        return doctorRepository.findAll()
    }

    override fun getDoctorById(id: Long): Doctor {
        return doctorRepository.findById(id).orElseThrow {
            throw RuntimeException("Doctor not found with id: $id")
        }
    }

    override fun addDetailsToDoctor(doctorDto: DoctorDto): MessageResponse {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name

        val user = doctorRepository.findByUsername(username)
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