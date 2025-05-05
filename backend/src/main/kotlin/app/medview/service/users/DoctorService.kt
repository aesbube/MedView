package app.medview.service.users

import app.medview.domain.dto.*
import app.medview.domain.dto.users.DoctorDto
import app.medview.domain.dto.users.DoctorUpdateRequestDto
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.dto.users.PatientRequestDto

interface DoctorService {
    fun getAllDoctors(): List<DoctorDto>
    fun getDoctorById(id: Long): DoctorDto
    fun addDetailsToDoctor(doctorUpdateRequestDto: DoctorUpdateRequestDto): MessageResponse
    fun addPatientToDoctor(patientRequestDto: PatientRequestDto): MessageResponse
    fun getCurrentDoctor(): DoctorDto
    fun getPatientsOfDoctor(): List<PatientDto>
    fun getPatientOfDoctor(patientId: Long): PatientDto
    fun getPrescriptionsOfPatientsOfDoctor(patientId: Long): List<PrescriptionDto>
    fun writePrescription(patientId: Long, prescriptionRequestDto: PrescriptionRequestDto): PrescriptionDto
    fun cancelPrescription(patientId: Long, prescriptionId: String): PrescriptionDto
    fun scheduleAppointment(patientId: Long, appointmentRequestDto: AppointmentRequestDto): MessageResponse
}