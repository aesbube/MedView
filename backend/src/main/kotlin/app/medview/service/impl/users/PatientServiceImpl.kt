package app.medview.service.impl.users

import app.medview.domain.Prescription
import app.medview.domain.Role
import app.medview.domain.dto.MessageResponse
import app.medview.domain.users.Doctor
import app.medview.domain.users.Patient
import app.medview.exceptions.PatientNotFoundException
import app.medview.repository.PatientRepository
import app.medview.service.PrescriptionService
import app.medview.service.users.PatientService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PatientServiceImpl(
    private val patientRepository: PatientRepository,
    private val prescriptionService: PrescriptionService
) : PatientService {

    val logger = org.slf4j.LoggerFactory.getLogger(PatientServiceImpl::class.java)


    override fun getAllPatients(): List<Patient> {
        return patientRepository.findAll()
    }

    override fun getPatientById(id: Long): Patient {
        return patientRepository.findById(id).orElseThrow {
            throw RuntimeException("Patient not found with id: $id")
        }
    }

    override fun getCurrentPatient(): Patient {
        logger.info(SecurityContextHolder.getContext().authentication.name)
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name

        return patientRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
    }

    override fun getPatientByEmail(email: String): Patient {
        return patientRepository.findByEmail(email)
            ?: throw PatientNotFoundException("email: $email")
    }

    override fun addDetailsToPatient(patient: Patient): MessageResponse {
        val auth = SecurityContextHolder.getContext().authentication

        if (patient.role != Role.PATIENT) {
            throw RuntimeException("User is not a patient")
        }

        val doctor: Doctor? = patient.doctor

        patient.doctor = doctor

        patientRepository.save(patient)
        return MessageResponse("Patient details added successfully")
    }

    override fun getPatientsByDoctor(doctorId: Long): List<Patient> {
        return patientRepository.findByDoctorId(doctorId)
    }

    override fun getPrescriptionsOfPatient(patientId: Long): List<Prescription> {
        return prescriptionService.getPrescriptionsByPatientId(patientId)
    }
}