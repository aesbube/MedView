package app.medview.service.users

import app.medview.domain.Prescription
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.PrescriptionDto
import app.medview.domain.dto.PrescriptionRequestDto
import app.medview.domain.dto.users.DoctorDto
import app.medview.domain.dto.users.DoctorUpdateRequestDto
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.users.Doctor
import app.medview.domain.users.Patient

interface DoctorService {
    fun getAllDoctors(): List<DoctorDto>
    fun getDoctorById(id: Long): DoctorDto
    fun addDetailsToDoctor(doctorUpdateRequestDto: DoctorUpdateRequestDto): MessageResponse

    fun getCurrentDoctor(): DoctorDto
    fun getPatientsOfDoctor() : List<PatientDto>
    fun getPatientOfDoctor(patientId: Long) : PatientDto
    fun getPrescriptionsOfPatientsOfDoctor(patientId:Long) : List<PrescriptionDto>
    fun writePrescription(patientId:Long, prescriptionRequestDto: PrescriptionRequestDto) : PrescriptionDto
    fun cancelPrescription(patientId:Long, prescriptionId: String) : PrescriptionDto
}