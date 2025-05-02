package app.medview.web

import app.medview.domain.Prescription
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.users.Patient
import app.medview.service.users.PatientService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/patients")
class PatientController(private val patientService: PatientService) {
    @GetMapping
    fun getAllPatients(): ResponseEntity<List<Patient>> {
        val patients = patientService.getAllPatients()
        return ResponseEntity.ok(patients)
    }

    @PostMapping("/update")
    fun addDetailsToPatient(@RequestBody patient: Patient): ResponseEntity<String> {
        val response = patientService.addDetailsToPatient(patient)
        return ResponseEntity.ok(response.message)
    }

    @GetMapping("/me")
    fun getCurrentPatient() : ResponseEntity<Patient> {
        return ResponseEntity(patientService.getCurrentPatient(), HttpStatus.OK)
    }

    @GetMapping("/me/prescriptions")
    fun getPrescriptionsOfCurrentUser() : ResponseEntity<List<Prescription>>{
        val patient = patientService.getCurrentPatient()
        return ResponseEntity(patientService.getPrescriptionsOfPatient(patient.id), HttpStatus.OK)
    }

}