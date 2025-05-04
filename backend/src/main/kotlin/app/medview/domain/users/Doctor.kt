package app.medview.domain.users

import app.medview.domain.Role
import app.medview.domain.User
import jakarta.persistence.*

@Entity
@Table(name = "doctors")
data class Doctor(
    var specialty: String = "",
    var licenseNumber: String = "",
    var yearsOfExperience: Int = 0,
    var hospitalName: String = "",
) : User(
    role = Role.DOCTOR
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val doctorId: Long = 0
}