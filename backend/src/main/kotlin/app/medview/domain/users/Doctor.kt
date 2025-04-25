package app.medview.domain.users

import app.medview.domain.Role
import app.medview.domain.User
import jakarta.persistence.Entity

@Entity
class Doctor(
    id: Long = 0,
    username: String = "",
    password: String = "",
    email: String = "",
    val specialty: String = "",
    val licenseNumber: String = "",
    val yearsOfExperience: Int = 0,
    val hospitalName: String = "",
) : User(
    id = id,
    username = username,
    password = password,
    email = email,
    role = Role.DOCTOR
)