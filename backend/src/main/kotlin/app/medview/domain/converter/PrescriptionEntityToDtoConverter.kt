package app.medview.domain.converter

import app.medview.domain.Prescription
import app.medview.domain.dto.PrescriptionDto
import org.springframework.stereotype.Component

@Component
class PrescriptionEntityToDtoConverter (
    private val patientConverter: PatientEntityToDtoConverter,
    private val doctorConverter: DoctorEntityToDtoConverter
){
    fun convert (prescription: Prescription) = PrescriptionDto(
        id = prescription.id,
        patientId = prescription.patient?.id,
        doctorId = prescription.doctor?.id,
        medicine = prescription.medicine,
        frequency = prescription.frequency,
        status = prescription.status,
        expirationDate = prescription.expirationDate
    )
}