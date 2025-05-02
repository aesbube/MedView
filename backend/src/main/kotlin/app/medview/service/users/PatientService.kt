package app.medview.service.users

import app.medview.domain.Prescription
import app.medview.domain.dto.MessageResponse
import app.medview.domain.users.Patient

interface PatientService {
    fun getAllPatients(): List<Patient>
    fun getPatientById(id: Long): Patient

    fun getCurrentPatient(): Patient
    fun getPatientByEmail(email: String): Patient
    fun addDetailsToPatient(patient: Patient): MessageResponse
    fun getPatientsByDoctor(doctorId: Long) : List<Patient>
    fun getPrescriptionsOfPatient(patientId: Long) : List<Prescription>
}