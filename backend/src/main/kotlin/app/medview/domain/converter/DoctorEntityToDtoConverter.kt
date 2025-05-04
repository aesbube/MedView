package app.medview.domain.converter

import app.medview.domain.dto.users.DoctorDto
import app.medview.domain.dto.users.PatientDto
import app.medview.domain.users.Doctor
import app.medview.domain.users.Patient
import org.springframework.stereotype.Component

@Component
class DoctorEntityToDtoConverter {
    fun convert (doctor: Doctor) = DoctorDto(
            username = doctor.username,
            email = doctor.email,
            specialty = doctor.specialty,
            licenseNumber = doctor.licenseNumber,
            yearsOfExperience = doctor.yearsOfExperience,
            hospitalName = doctor.hospitalName
        )
}