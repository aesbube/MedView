package app.medview.service.impl.users

import app.medview.domain.Prescription
import app.medview.domain.Role
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.PrescriptionRequestDto
import app.medview.domain.users.Doctor
import app.medview.domain.users.Patient
import app.medview.exceptions.IllegalDoctorPatientOperation
import app.medview.repository.DoctorRepository
import app.medview.service.PrescriptionService
import app.medview.service.users.DoctorService
import app.medview.service.users.PatientService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class DoctorServiceImpl(private val doctorRepository: DoctorRepository,
                        private val patientService: PatientService,
                        private val prescriptionService: PrescriptionService
) :
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

    override fun addDetailsToDoctor(doctor: Doctor): MessageResponse {
        logger.info("Adding doctor details")
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name

        logger.info("Adding doctor details for user: $username")
        logger.info("Doctor DTO: $doctor")

        if (doctor.role != Role.DOCTOR) {
            throw RuntimeException("User is not a doctor")
        }

        doctor.specialty = doctor.specialty
        doctor.licenseNumber = doctor.licenseNumber
        doctor.yearsOfExperience = doctor.yearsOfExperience
        doctor.hospitalName = doctor.hospitalName

        doctorRepository.save(doctor)
        return MessageResponse("Doctor details added successfully")
    }

    override fun getCurrentDoctor(): Doctor {
        logger.info(SecurityContextHolder.getContext().authentication.name)
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name

        return doctorRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
    }

    override fun getPatientOfDoctor(doctorId: Long, patientId:Long): Patient {
        val patient = patientService.getPatientById(patientId)
        if (patient.doctor != doctorRepository.findById(doctorId).get())
            throw IllegalDoctorPatientOperation (doctorId,patientId)
        return patient
    }

    override fun getPatientsOfDoctor(doctorId: Long): List<Patient> {
        return patientService.getPatientsByDoctor(doctorId)
    }

    override fun getPrescriptionsOfPatientsOfDoctor(doctorId: Long, patientId:Long): List<Prescription> {
        return prescriptionService.getPrescriptionsByPatientId(patientId)
    }

    override fun writePrescription(doctorId: Long, patientId:Long, prescriptionRequestDto: PrescriptionRequestDto) : Prescription {
        return prescriptionService.create(patientId,doctorId,prescriptionRequestDto)
    }

    override fun cancelPrescription(doctorId: Long, patientId:Long, prescriptionId: String): Prescription {
        return prescriptionService.cancel(patientId,doctorId, prescriptionId)
    }


}