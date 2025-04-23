package app.medview.domain.enitities

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "patients")
data class PatientEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    val patientId: Long? = null,

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    val user: OldUserEntity? = null,

    @Column(name = "date_of_birth", nullable = false)
    val dateOfBirth: LocalDate,

    @Column(name = "gender")
    val gender: String? = null,
)