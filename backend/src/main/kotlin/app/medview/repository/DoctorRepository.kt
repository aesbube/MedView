package app.medview.repository

import app.medview.domain.users.Doctor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DoctorRepository : JpaRepository<Doctor, Long> {
    fun findByUsername(username: String): Doctor?
    fun findByUsernameContainingIgnoreCase(username: String): List<Doctor>
    fun existsByUsername(username: String): Boolean
}