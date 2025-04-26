package app.medview.web

import app.medview.domain.dto.users.SpecialistDto
import app.medview.domain.users.Specialist
import app.medview.service.users.SpecialistService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/specialists")
class SpecialistController(private val specialistService: SpecialistService) {
    @GetMapping
    fun getAllSpecialists(): ResponseEntity<List<Specialist>> {
        val specialists = specialistService.getAllSpecialists()
        return ResponseEntity.ok(specialists)
    }

    @PostMapping("/update")
    fun addDetailsToSpecialist(@RequestBody specialistDto: SpecialistDto): ResponseEntity<String> {
        val response = specialistService.addDetailsToSpecialist(specialistDto)
        return ResponseEntity.ok(response.message)
    }
}