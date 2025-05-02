package app.medview.service.users

import app.medview.domain.Prescription
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.PrescriptionDto
import app.medview.domain.dto.PrescriptionScanDto
import app.medview.domain.dto.users.PharmacistDto
import app.medview.domain.users.Pharmacist

interface PharmacistService {
    fun getAllPharmacists(): List<Pharmacist>
    fun getPharmacistById(id: Long): Pharmacist
    fun addDetailsToPharmacist(pharmacist: Pharmacist): MessageResponse

    fun getCurrentPharmacist(): Pharmacist
    fun getPrescription(pharmacistId: Long, prescriptionScanDto: PrescriptionScanDto) : Prescription
    fun validatePrescription(pharmacistId: Long, prescriptionScanDto: PrescriptionScanDto) : Prescription
}