package app.medview.service.users

import app.medview.domain.Prescription
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.PrescriptionRequestDto
import app.medview.domain.users.Doctor
import app.medview.domain.users.Patient

interface DoctorService {
    fun getAllDoctors(): List<Doctor>
    fun getDoctorById(id: Long): Doctor
    fun addDetailsToDoctor(doctor: Doctor): MessageResponse

    fun getCurrentDoctor(): Doctor
    fun getPatientOfDoctor(doctorId: Long, patientId: Long) : Patient
    fun getPatientsOfDoctor(doctorId: Long) : List<Patient>
    fun getPrescriptionsOfPatientsOfDoctor(doctorId: Long, patientId:Long) : List<Prescription>
    fun writePrescription(doctorId: Long, patientId:Long, prescriptionRequestDto: PrescriptionRequestDto) : Prescription
    fun cancelPrescription(doctorId: Long, patientId:Long, prescriptionId: String) : Prescription
}