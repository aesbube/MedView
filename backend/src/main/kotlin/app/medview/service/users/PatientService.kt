package app.medview.service.users

import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.users.Patient

interface PatientService {
    fun getAllPatients(): List<Patient>
    fun getPatientById(id: Long): Patient
    fun addDetailsToPatient(patientDto: PatientDto): MessageResponse
}