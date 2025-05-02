package app.medview.domain.converter

import app.medview.domain.Prescription
import app.medview.domain.dto.PrescriptionDto
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.users.Patient
import org.springframework.stereotype.Component

@Component
class PatientEntityToDtoConverter {
    fun convert (patient: Patient) : PatientDto = PatientDto(
            username = patient.username,
            email = patient.email,
            doctorId = patient.doctor?.id
        )
}