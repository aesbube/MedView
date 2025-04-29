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
    var schedule: Schedule? = null,
    @ManyToOne
    var patient: Patient? = null,
    @ManyToOne
    val assignee: User? = null,
    var date: Date? = null,
    var time: String = "",
    var location: String = "",
)