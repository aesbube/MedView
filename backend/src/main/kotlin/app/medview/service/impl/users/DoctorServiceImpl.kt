package app.medview.service.impl.users

import app.medview.domain.Prescription
import app.medview.domain.Role
import app.medview.domain.converter.DoctorEntityToDtoConverter
import app.medview.domain.converter.PatientEntityToDtoConverter
import app.medview.domain.converter.PrescriptionEntityToDtoConverter
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.PrescriptionDto
import app.medview.domain.dto.PrescriptionRequestDto
import app.medview.domain.dto.users.DoctorDto
import app.medview.domain.dto.users.DoctorUpdateRequestDto
import app.medview.domain.dto.users.PatientDto
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
                        private val prescriptionService: PrescriptionService,
                        private val doctorConverter: DoctorEntityToDtoConverter,
                        private val patientConverter: PatientEntityToDtoConverter,
                        private val prescriptionConverter: PrescriptionEntityToDtoConverter
) :
    DoctorService {
    val logger = org.slf4j.LoggerFactory.getLogger(DoctorServiceImpl::class.java)
    override fun getAllDoctors(): List<DoctorDto> {
        return doctorRepository.findAll().map { doctorConverter.convert(it) }
    }

    override fun getDoctorById(id: Long): DoctorDto {
        return doctorConverter.convert(doctorRepository.findById(id).orElseThrow {
            throw RuntimeException("Doctor not found with id: $id")
        })
    }

    override fun addDetailsToDoctor(doctorUpdateRequestDto: DoctorUpdateRequestDto): MessageResponse {
        logger.info("Adding doctor details")
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name

        val doctor = doctorRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        logger.info("Adding doctor details for user: $username")
        logger.info("Doctor DTO: $doctorUpdateRequestDto")

        val updateDoctor = doctor.copy(
            specialty = doctorUpdateRequestDto.specialty ?: doctor.specialty,
            licenseNumber = doctorUpdateRequestDto.licenseNumber ?: doctor.licenseNumber,
            yearsOfExperience = doctorUpdateRequestDto.yearsOfExperience ?: doctor.yearsOfExperience,
            hospitalName = doctorUpdateRequestDto.hospitalName ?: doctor.hospitalName,
        )

        doctorRepository.save(updateDoctor)
        return MessageResponse("Doctor details added successfully")
    }

    override fun getCurrentDoctor(): DoctorDto {
        logger.info(SecurityContextHolder.getContext().authentication.name)
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name

        return doctorConverter.convert(doctorRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username"))
    }

    override fun getPatientOfDoctor(doctorId: Long, patientId:Long): PatientDto {
        val patient = patientService.getPatientById(patientId)

        if (patient.doctor != getDoctorById(doctorId))
            throw IllegalDoctorPatientOperation (doctorId,patientId)

        return patient
    }

    override fun getPatientsOfDoctor(doctorId: Long): List<PatientDto> {
        return patientService.getPatientsByDoctor(doctorId)
    }

    override fun getPrescriptionsOfPatientsOfDoctor(doctorId: Long, patientId:Long): List<PrescriptionDto> {
        return prescriptionService.getPrescriptionsByPatientId(patientId).map { prescriptionConverter.convert(it) }
    }

    override fun writePrescription(doctorId: Long, patientId:Long, prescriptionRequestDto: PrescriptionRequestDto) : PrescriptionDto {
        return prescriptionConverter.convert(
            prescriptionService.create(patientId,doctorId,prescriptionRequestDto)
        )
    }

    override fun cancelPrescription(doctorId: Long, patientId:Long, prescriptionId: String): PrescriptionDto {
        return prescriptionConverter.convert(
            prescriptionService.cancel(patientId,doctorId, prescriptionId)
        )
    }


}