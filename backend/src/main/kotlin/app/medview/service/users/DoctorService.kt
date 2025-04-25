package app.medview.service.users

import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.users.DoctorDto
import app.medview.domain.users.Doctor

interface DoctorService {
    fun getAllDoctors(): List<Doctor>
    fun getDoctorById(id: Long): Doctor
    fun addDetailsToDoctor(doctorDto: DoctorDto): MessageResponse
}