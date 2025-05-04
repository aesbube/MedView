package app.medview.domain.users

import app.medview.domain.Role
import app.medview.domain.User
import jakarta.persistence.*

@Entity
@Table(name = "patients")
data class Patient(
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    var doctor: Doctor? = null,
) : User(
    role = Role.PATIENT
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val patientId: Long = 0
}
