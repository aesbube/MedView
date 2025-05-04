package app.medview.domain.users

import app.medview.domain.Role
import app.medview.domain.User
import jakarta.persistence.*

@Entity
@Table(name = "specialists")
data class Specialist(
    var specialty: String = "",
    var licenseNumber: String = "",
    var yearsOfExperience: Int = 0,
) : User(
    role = Role.SPECIALIST
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val specialistId: Long = 0
}