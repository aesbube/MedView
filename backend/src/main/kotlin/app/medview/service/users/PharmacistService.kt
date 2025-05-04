package app.medview.service.users

import app.medview.domain.Prescription
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.PrescriptionDto
import app.medview.domain.dto.PrescriptionScanDto
import app.medview.domain.dto.users.PharmacistDto
import app.medview.domain.dto.users.PharmacistUpdateRequestDto
import app.medview.domain.users.Pharmacist

interface PharmacistService {
    fun getAllPharmacists(): List<PharmacistDto>
    fun getPharmacistById(id: Long): PharmacistDto
    fun addDetailsToPharmacist(pharmacistUpdateRequestDto: PharmacistUpdateRequestDto): MessageResponse
    fun getCurrentPharmacist(): PharmacistDto
    fun getPrescription(pharmacistId: Long, prescriptionScanDto: PrescriptionScanDto) : PrescriptionDto
    fun validatePrescription(pharmacistId: Long, prescriptionScanDto: PrescriptionScanDto) : PrescriptionDto
}