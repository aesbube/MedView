package app.medview.domain.users

import app.medview.domain.Role
import app.medview.domain.User
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class Patient(
    id: Long = 0,
    username: String = "",
    password: String = "",
    email: String = "",
    @ManyToOne
    val doctor: Doctor? = null,
) : User(
    id = id,
    username = username,
    password = password,
    email = email,
    role = Role.PATIENT
)
