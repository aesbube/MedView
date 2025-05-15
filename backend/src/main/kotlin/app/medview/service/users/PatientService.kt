package app.medview.service.users

import app.medview.domain.Diagnosis
import app.medview.domain.Prescription
import app.medview.domain.dto.AppointmentDto
import app.medview.domain.dto.DiagnosisDto
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.PrescriptionDto
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.dto.users.PatientRequestDto
import app.medview.domain.users.Patient

interface PatientService {
    fun getPatientById(id: Long): PatientDto
    fun getCurrentPatient(): PatientDto
    fun addDetailsToPatient(patientDto: PatientDto): PatientDto
    fun getPatientsByDoctor(doctorId: Long): List<PatientDto>
    fun getPrescriptionsOfPatient(): List<PrescriptionDto>
    fun getAppointmentOfPatient(refNumber: String): AppointmentDto
    fun getAllAppointmentsOfPatient(): List<AppointmentDto>
}