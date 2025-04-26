package app.medview.web

import app.medview.domain.dto.users.PharmacistDto
import app.medview.domain.users.Pharmacist
import app.medview.service.users.PharmacistService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pharmacists")
class PharmacistController(private val pharmacistService: PharmacistService) {
    @GetMapping
    fun getAllDoctors(): ResponseEntity<List<Pharmacist>> {
        val pharmacists = pharmacistService.getAllPharmacists()
        return ResponseEntity.ok(pharmacists)
    }

    @PostMapping("/update")
    fun addDetailsToDoctor(@RequestBody pharmacistDto: PharmacistDto): ResponseEntity<String> {
        val response = pharmacistService.addDetailsToPharmacist(pharmacistDto)
        return ResponseEntity.ok(response.message)
    }
}