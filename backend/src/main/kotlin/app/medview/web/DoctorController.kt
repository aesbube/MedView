package app.medview.web

import app.medview.domain.Prescription
import app.medview.domain.dto.PrescriptionDto
import app.medview.domain.dto.PrescriptionRequestDto
import app.medview.domain.dto.users.DoctorDto
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.users.Doctor
import app.medview.domain.users.Patient
import app.medview.service.users.DoctorService
import jdk.jfr.Frequency
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/doctors")
class DoctorController(private val doctorService: DoctorService) {
    @GetMapping
    fun getAllDoctors(): ResponseEntity<List<DoctorDto>> {
        val doctors = doctorService.getAllDoctors()
        return ResponseEntity.ok(doctors)
    }

    @PostMapping("/update")
    fun addDetailsToDoctor(@RequestBody doctorDto: DoctorDto): ResponseEntity<String> {
        val response = doctorService.addDetailsToDoctor(doctorDto)
        return ResponseEntity.ok(response.message)
    }

    @GetMapping("/me")
    fun getCurrentDoctor():ResponseEntity<Doctor> {
        return ResponseEntity.ok(doctorService.getCurrentDoctor())
    }

    @GetMapping("/me/patients")
    fun getPatientsOfDoctor() : ResponseEntity<List<Patient>>{
        val doctor = doctorService.getCurrentDoctor()
        return ResponseEntity.ok(doctorService.getPatientsOfDoctor(doctor.id))
    }

    @GetMapping("/me/patients/{id}")
    fun getPatientOfDoctor(@PathVariable ("id") patientId : Long) : ResponseEntity<Patient>{
        val doctor = doctorService.getCurrentDoctor()
        return ResponseEntity.ok(doctorService.getPatientOfDoctor(doctor.id, patientId))
    }

    @GetMapping("/me/patients/{id}/prescriptions")
    fun getPrescriptionsOfPatientOfDoctor(@PathVariable ("id") patientId: Long) : ResponseEntity<List<Prescription>>{
        val doctor = doctorService.getCurrentDoctor()
        return ResponseEntity.ok(doctorService.getPrescriptionsOfPatientsOfDoctor(doctor.id, patientId))
    }

    @PostMapping("/me/patients/{id}/prescriptions/new")
    fun writePrescription(
        @PathVariable ("id") patientId: Long,
        @RequestBody prescription: PrescriptionRequestDto
        ) : ResponseEntity<Prescription>{
        val doctor = doctorService.getCurrentDoctor()

        return ResponseEntity(doctorService.writePrescription(doctor.id, patientId, prescription), HttpStatus.CREATED)
    }

    @PostMapping("/me/patients/{patientId}/prescriptions/{prescriptionId}")
    fun cancelPrescription(
        @PathVariable ("patientId") patientId: Long,
        @PathVariable ("prescriptionId") prescriptionId: String,
    ) : ResponseEntity<Prescription>{
        val doctor = doctorService.getCurrentDoctor()

        return ResponseEntity(doctorService.cancelPrescription(doctor.id,patientId,prescriptionId), HttpStatus.OK)
    }
}
