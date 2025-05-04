package app.medview.domain.users

import app.medview.domain.Role
import app.medview.domain.User
import jakarta.persistence.*

@Entity
@Table(name = "specialists")
@PrimaryKeyJoinColumn(name = "id")
data class Specialist(
    var specialty: String = "",
    var licenseNumber: String = "",
    var yearsOfExperience: Int = 0,
) : User(
    role = Role.SPECIALIST
)