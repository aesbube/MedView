package app.medview.domain.enitities

import jakarta.persistence.*

@Entity
@Table(name = "doctors")
data class DoctorEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    val doctorId: Long? = null,

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    val user: OldUserEntity? = null,

    @Column(name = "specialty_id", nullable = false)
    val specialtyId: Int,
)