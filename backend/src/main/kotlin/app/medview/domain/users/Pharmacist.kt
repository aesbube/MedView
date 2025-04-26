package app.medview.domain.users

import app.medview.domain.Role
import app.medview.domain.User
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "pharmacists")
class Pharmacist(
    id: Long = 0,
    username: String = "",
    password: String = "",
    email: String = "",
    var pharmacyName: String = "",
    var pharmacyAddress: String = "",
    var licenseNumber: Int = 0,
) : User(
    id = id,
    username = username,
    password = password,
    email = email,
    role = Role.PHARMACIST
)