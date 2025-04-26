package app.medview.domain.users

import app.medview.domain.Role
import app.medview.domain.User
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "specialists")
class Specialist(
    id: Long = 0,
    username: String = "",
    password: String = "",
    email: String = "",
    var specialty: String = "",
    var licenseNumber: String = "",
    var yearsOfExperience: Int = 0,
) : User(
    id = id,
    username = username,
    password = password,
    email = email,
    role = Role.SPECIALIST
)