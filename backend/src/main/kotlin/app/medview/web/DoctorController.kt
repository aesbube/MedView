package app.medview.web

import app.medview.domain.dto.users.DoctorDto
import app.medview.domain.users.Doctor
import app.medview.service.users.DoctorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/doctors")
class DoctorController(private val doctorService: DoctorService) {
    @GetMapping
    fun getAllDoctors(): ResponseEntity<List<Doctor>> {
        val doctors = doctorService.getAllDoctors()
        return ResponseEntity.ok(doctors)
    }

    @PostMapping("/update")
    fun addDetailsToDoctor(doctorDto: DoctorDto): ResponseEntity<String> {
        val response = doctorService.addDetailsToDoctor(doctorDto)
        return ResponseEntity.ok(response.message)
    }
}