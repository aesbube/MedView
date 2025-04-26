package app.medview.service.impl.users

import app.medview.domain.Role
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.users.PharmacistDto
import app.medview.domain.users.Pharmacist
import app.medview.repository.PharmacistRepository
import app.medview.repository.UserRepository
import app.medview.service.users.PharmacistService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class PharmacistServiceImpl(
    private val pharmacistRepository: PharmacistRepository,
    private val userRepository: UserRepository
) : PharmacistService {
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

        val pharmacist = pharmacistRepository.findByUsername(username)
            ?: throw RuntimeException("Pharmacist not found with username: $username")

        if (pharmacist.role != Role.PHARMACIST) {
            throw RuntimeException("User is not a pharmacist")
        }

        pharmacist.pharmacyName = pharmacistDto.pharmacyName
        pharmacist.pharmacyAddress = pharmacistDto.pharmacyAddress
        pharmacist.licenseNumber = pharmacistDto.licenseNumber

        pharmacistRepository.save(pharmacist)
        return MessageResponse("Pharmacist details added successfully")
    }
}