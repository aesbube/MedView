package app.medview.service.impl.users

import app.medview.domain.Prescription
import app.medview.domain.Role
import app.medview.domain.converter.PatientEntityToDtoConverter
import app.medview.domain.converter.PrescriptionEntityToDtoConverter
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.PrescriptionDto
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.dto.users.PatientRequestDto
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
    private val prescriptionService: PrescriptionService,
    private val patientConverter: PatientEntityToDtoConverter,
    private val prescriptionConverter: PrescriptionEntityToDtoConverter
) : PatientService {

    val logger = org.slf4j.LoggerFactory.getLogger(PatientServiceImpl::class.java)


    override fun getAllPatients(): List<PatientDto> {
        return patientRepository.findAll().map { patientConverter.convert(it) }
    }

    override fun getPatientById(id: Long): PatientDto {
        return patientConverter.convert(patientRepository.findById(id).orElseThrow {
            throw RuntimeException("Patient not found with id: $id")
        })
    }

    override fun getCurrentPatient(): PatientDto {
        logger.info(SecurityContextHolder.getContext().authentication.name)
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name

        return patientConverter.convert(patientRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username"))
    }

    override fun getPatientByEmail(email: String): PatientDto {
        return patientConverter.convert(patientRepository.findByEmail(email)
            ?: throw PatientNotFoundException("email: $email"))
    }

    override fun addDetailsToPatient(patientRequestDto: PatientRequestDto): MessageResponse {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name

        val patient = patientRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")


        if (patient.role != Role.PATIENT) {
            throw RuntimeException("User is not a patient")
        }

        val updatedPatient = patient.copy(
            doctor = patientRequestDto.doctor ?: patient.doctor,
        )

        patientRepository.save(updatedPatient)
        return MessageResponse("Patient details added successfully")
    }

    override fun getPatientsByDoctor(doctorId: Long): List<PatientDto> {
        return patientRepository.findByDoctorId(doctorId).map { patientConverter.convert(it) }
    }

    override fun getPrescriptionsOfPatient(patientId: Long): List<PrescriptionDto> {
        return prescriptionService.getPrescriptionsByPatientId(patientId).map { prescriptionConverter.convert(it) }
    }
}