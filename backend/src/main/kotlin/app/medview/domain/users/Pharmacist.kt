package app.medview.domain.users

import app.medview.domain.Role
import app.medview.domain.User
import jakarta.persistence.*

@Entity
@Table(name = "pharmacists")
data class Pharmacist(
    var pharmacyName: String = "",
    var pharmacyAddress: String = "",
    var licenseNumber: Int = 0,
) : User(
    role = Role.PHARMACIST
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pharmacistId: Long = 0
}