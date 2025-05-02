package app.medview.service.impl.users

import app.medview.domain.Prescription
import app.medview.domain.Role
import app.medview.domain.converter.PharmacistEntityToDtoConverter
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.PrescriptionScanDto
import app.medview.domain.users.Pharmacist
import app.medview.exceptions.*
import app.medview.repository.PatientRepository
import app.medview.repository.PharmacistRepository
import app.medview.service.PrescriptionService
import app.medview.service.users.PharmacistService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PharmacistServiceImpl(
    private val pharmacistRepository: PharmacistRepository,
    private val prescriptionService: PrescriptionService,
    private val patientRepository: PatientRepository
) : PharmacistService {

    val logger = org.slf4j.LoggerFactory.getLogger(PharmacistServiceImpl::class.java)

    override fun getAllPharmacists(): List<Pharmacist> {
        return pharmacistRepository.findAll()
    }

    override fun getPharmacistById(id: Long): Pharmacist {
        return pharmacistRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Pharmacist not found with id: $id")
        }
    }

    override fun addDetailsToPharmacist(pharmacist: Pharmacist): MessageResponse {
        val auth = SecurityContextHolder.getContext().authentication

        if (pharmacist.role != Role.PHARMACIST) {
            throw RuntimeException("User is not a pharmacist")
        }

        pharmacist.pharmacyName = pharmacist.pharmacyName
        pharmacist.pharmacyAddress = pharmacist.pharmacyAddress
        pharmacist.licenseNumber = pharmacist.licenseNumber

        pharmacistRepository.save(pharmacist)
        return MessageResponse("Pharmacist details added successfully")
    }

    override fun getCurrentPharmacist(): Pharmacist {
        logger.info(SecurityContextHolder.getContext().authentication.name)
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name

        return pharmacistRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
    }

    override fun getPrescription(pharmacistId: Long, prescriptionScanDto: PrescriptionScanDto): Prescription {
        val patientId = prescriptionScanDto.patientId
        val prescriptionId = prescriptionScanDto.prescriptionId
        val prescription = prescriptionService.getPrescriptionById(prescriptionId)

        if (patientRepository.getById(patientId) != prescriptionService.getPrescriptionById(prescriptionId).patient)
            throw IllegalPrescriptionRedeemerException(prescriptionId,patientId)
        return prescription
    }

    override fun validatePrescription(pharmacistId: Long, prescriptionScanDto: PrescriptionScanDto) : Prescription {
        return prescriptionService.redeem(pharmacistId,prescriptionScanDto.prescriptionId,prescriptionScanDto.patientId)
    }
}