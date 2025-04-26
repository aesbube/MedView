package app.medview.domain.users

import app.medview.domain.Role
import app.medview.domain.User
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "patients")
class Patient(
    id: Long = 0,
    username: String = "",
    password: String = "",
    email: String = "",
    @ManyToOne
    var doctor: Doctor? = null,
) : User(
    id = id,
    username = username,
    password = password,
    email = email,
    role = Role.PATIENT
)
