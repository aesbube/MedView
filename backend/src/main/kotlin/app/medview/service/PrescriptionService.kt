package app.medview.service

import app.medview.domain.Prescription
import app.medview.domain.dto.PrescriptionRequestDto

interface PrescriptionService {

    fun create(patientId: Long, doctorId: Long, prescriptionRequestDto: PrescriptionRequestDto) : Prescription
    fun redeem(pharmacistId: Long, prescriptionId: String, patientId: Long) : Prescription
    fun cancel(patientId: Long, doctorId: Long, prescriptionId: String): Prescription
    fun getPrescriptionById(prescriptionId: String) : Prescription
    fun getPrescriptionsByPatientId(patientId: Long) : List<Prescription>

}