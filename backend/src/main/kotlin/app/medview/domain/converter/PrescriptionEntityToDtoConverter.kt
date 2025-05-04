package app.medview.domain.converter

import app.medview.domain.Prescription
import app.medview.domain.dto.PrescriptionDto
import app.medview.exceptions.NullDoctorException
import app.medview.exceptions.NullPatientException

class PrescriptionEntityToDtoConverter (
    private val patientConverter: PatientEntityToDtoConverter,
    private val doctorConverter: DoctorEntityToDtoConverter
){
    fun convert (prescription: Prescription) = PrescriptionDto(
        id = prescription.id,
        patient = patientConverter.convert(prescription.patient ?: throw NullPatientException()),
        doctor = doctorConverter.convert(prescription.doctor ?: throw NullDoctorException()),
        medicine = prescription.medicine,
        frequency = prescription.frequency,
        status = prescription.status,
        expirationDate = prescription.expirationDate
    )
}