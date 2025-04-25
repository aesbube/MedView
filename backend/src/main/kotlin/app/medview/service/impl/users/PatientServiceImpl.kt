package app.medview.service.impl.users

import app.medview.domain.Role
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.users.Doctor
import app.medview.domain.users.Patient
import app.medview.repository.PatientRepository
import app.medview.repository.UserRepository
import app.medview.service.users.DoctorService
import app.medview.service.users.PatientService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class PatientServiceImpl(
    private val patientRepository: PatientRepository,
    private val doctorService: DoctorService,
    private val userRepository: UserRepository
) : PatientService {
    override fun getAllPatients(): List<Patient> {
        return patientRepository.findAll()
    }

    override fun getPatientById(id: Long): Patient {
        return patientRepository.findById(id).orElseThrow {
            throw RuntimeException("Patient not found with id: $id")
        }
    }

    override fun addDetailsToPatient(patientDto: PatientDto): MessageResponse {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name

        val user = userRepository.findByUsername(username)
            ?: throw RuntimeException("Patient not found with username: $username")

        if (user.role != Role.PATIENT) {
            throw RuntimeException("User is not a patient")
        }

        val doctor: Doctor = doctorService.getDoctorById(patientDto.doctorId)

        val patient = Patient(
            id = user.id,
            username = user.username,
            password = user.password,
            email = user.email,
            doctor = doctor,
        )

        patientRepository.save(patient)
        return MessageResponse("Patient details added successfully")
    }
}