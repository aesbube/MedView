package app.medview.service.users

import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.users.PharmacistDto
import app.medview.domain.users.Pharmacist

interface PharmacistService {
    fun getAllPharmacists(): List<Pharmacist>
    fun getPharmacistById(id: Long): Pharmacist
    fun addDetailsToPharmacist(pharmacistDto: PharmacistDto): MessageResponse
}