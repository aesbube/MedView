package app.medview.repository

import app.medview.domain.users.Doctor
import app.medview.domain.users.Patient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PatientRepository : JpaRepository<Patient, Long> {
    fun findByUsername(username: String): Patient?
    fun findByEmail(email: String): Patient?
    fun existsByUsername(username: String): Boolean
    fun findByDoctor(doctor: Doctor) : List<Patient>
    fun findByDoctorId(doctorId: Long) : List<Patient>
}