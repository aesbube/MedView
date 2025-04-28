package app.medview.domain

import app.medview.domain.users.Patient
import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "appointments")
class Appointment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne
    val schedule: Schedule? = null,
    @ManyToOne
    val patient: Patient? = null,
    @ManyToOne
    val assignee: User? = null,
    val date: Date? = null,
    val time: String = "",
    val location: String = "",
)