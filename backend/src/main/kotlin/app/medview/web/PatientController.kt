package app.medview.web

import app.medview.domain.Prescription
import app.medview.domain.dto.AppointmentDto
import app.medview.domain.dto.DiagnosisDto
import app.medview.domain.dto.PrescriptionDto
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.dto.users.PatientRequestDto
import app.medview.domain.users.Patient
import app.medview.service.users.PatientService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    fun getCurrentPatient(): ResponseEntity<PatientDto> {
        return ResponseEntity(patientService.getCurrentPatient(), HttpStatus.OK)
    }

    @GetMapping("/me/prescriptions")
    fun getPrescriptionsOfCurrentUser(): ResponseEntity<List<PrescriptionDto>> {
        return ResponseEntity(patientService.getPrescriptionsOfPatient(), HttpStatus.OK)
    }

    @GetMapping("/appointment/{refNumber}")
    fun getAppointmentByRefNumber(@PathVariable refNumber: String): ResponseEntity<AppointmentDto> {
        val appointment = patientService.getAppointmentOfPatient(refNumber)
        return ResponseEntity.ok(appointment)
    }

    @GetMapping("/appointments")
    fun getAllAppointments(): ResponseEntity<List<AppointmentDto>> {
        val appointments = patientService.getAllAppointmentsOfPatient()
        return ResponseEntity.ok(appointments)
    }

    @GetMapping("/diagnosis")
    fun getAllDiagnosis(): ResponseEntity<List<DiagnosisDto>> {
        val diagnosis = patientService.getAllDiagnosis()
        return ResponseEntity.ok(diagnosis)
    }

}