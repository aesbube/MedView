package app.medview.web

import app.medview.domain.dto.users.PatientDto
import app.medview.domain.users.Patient
import app.medview.service.users.PatientService
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
    fun addDetailsToPatient(@RequestBody patientDto: PatientDto): ResponseEntity<String> {
        val response = patientService.addDetailsToPatient(patientDto)
        return ResponseEntity.ok(response.message)
    }

}