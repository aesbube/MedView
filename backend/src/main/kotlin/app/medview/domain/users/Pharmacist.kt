package app.medview.domain.users

import app.medview.domain.Role
import app.medview.domain.User
import jakarta.persistence.Entity

@Entity
class Pharmacist(
    id: Long = 0,
    username: String = "",
    password: String = "",
    email: String = "",
    val pharmacyName: String = "",
    val pharmacyAddress: String = "",
    val licenseNumber: Int = 0,
) : User(
    id = id,
    username = username,
    password = password,
    email = email,
    role = Role.PHARMACIST
)