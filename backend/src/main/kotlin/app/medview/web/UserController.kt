package app.medview.web

import app.medview.domain.User
import app.medview.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    @GetMapping("/me")
    fun getCurrentUser(): ResponseEntity<User> {
        return ResponseEntity.ok(userService.getCurrentUser())
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        return ResponseEntity.ok(userService.getUserById(id))
    }

    @GetMapping("/patient")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    fun getPatientUsers(): Any {
        return mapOf("message" to "Patient users")
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getAdminUsers(): Any {
        return mapOf("message" to "Admin users")
    }

    @GetMapping("/specialist")
    @PreAuthorize("hasRole('ROLE_SPECIALIST')")
    fun getSpecialistUsers(): Any {
        return mapOf("message" to "Specialist users")
    }

    @GetMapping("/pharmacist")
    @PreAuthorize("hasRole('ROLE_PHARMACIST')")
    fun getPharmacistUsers(): Any {
        return mapOf("message" to "Pharmacist users")
    }
}