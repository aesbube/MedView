package app.medview.service.impl.users

import app.medview.domain.Role
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.users.PharmacistDto
import app.medview.domain.users.Pharmacist
import app.medview.repository.PharmacistRepository
import app.medview.service.users.PharmacistService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class PharmacistServiceImpl(private val pharmacistRepository: PharmacistRepository) : PharmacistService {
    override fun getAllPharmacists(): List<Pharmacist> {
        return pharmacistRepository.findAll()
    }

    override fun getPharmacistById(id: Long): Pharmacist {
        return pharmacistRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Pharmacist not found with id: $id")
        }
    }

    override fun addDetailsToPharmacist(pharmacistDto: PharmacistDto): MessageResponse {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name

        val user = pharmacistRepository.findByUsername(username)
            ?: throw RuntimeException("Pharmacist not found with username: $username")

        if (user.role != Role.PHARMACIST) {
            throw RuntimeException("User is not a pharmacist")
        }

        val pharmacist = Pharmacist(
            id = user.id,
            username = user.username,
            password = user.password,
            email = user.email,
            pharmacyName = pharmacistDto.pharmacyName,
            pharmacyAddress = pharmacistDto.pharmacyAddress,
            licenseNumber = pharmacistDto.licenseNumber,
        )

        pharmacistRepository.save(pharmacist)
        return MessageResponse("Pharmacist details added successfully")
    }
}