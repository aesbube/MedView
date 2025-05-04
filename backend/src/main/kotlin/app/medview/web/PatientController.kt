package app.medview.web

import app.medview.domain.Prescription
import app.medview.domain.dto.PrescriptionDto
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.dto.users.PatientRequestDto
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
    fun getAllPatients(): ResponseEntity<List<PatientDto>> {
        val patients = patientService.getAllPatients()
        return ResponseEntity.ok(patients)
    }

    @PostMapping("/update")
    fun addDetailsToPatient(@RequestBody patientRequestDto: PatientRequestDto): ResponseEntity<String> {
        val response = patientService.addDetailsToPatient(patientRequestDto)
        return ResponseEntity.ok(response.message)
    }

    @GetMapping("/me")
    fun getCurrentPatient() : ResponseEntity<PatientDto> {
        return ResponseEntity(patientService.getCurrentPatient(), HttpStatus.OK)
    }

    @GetMapping("/me/prescriptions")
    fun getPrescriptionsOfCurrentUser() : ResponseEntity<List<PrescriptionDto>>{
        return ResponseEntity(patientService.getPrescriptionsOfPatient(), HttpStatus.OK)
    }

}