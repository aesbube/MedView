package app.medview.service.users

import app.medview.domain.Prescription
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.PrescriptionDto
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.dto.users.PatientRequestDto
import app.medview.domain.users.Patient

interface PatientService {
    fun getAllPatients(): List<PatientDto>
    fun getPatientById(id: Long): PatientDto

    fun getCurrentPatient(): PatientDto
    fun getPatientByEmail(email: String): PatientDto
    fun addDetailsToPatient(patientRequestDto: PatientRequestDto): MessageResponse
    fun getPatientsByDoctor(doctorId: Long) : List<PatientDto>
    fun getPrescriptionsOfPatient(patientId: Long) : List<PrescriptionDto>
}