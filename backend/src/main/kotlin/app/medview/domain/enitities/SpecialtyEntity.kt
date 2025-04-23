package app.medview.domain.enitities

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class SpecialtyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialty_id")
    val roleId: Int? = null,

    @Column(name = "specialty_name", nullable = false, unique = true)
    val specialtyName: String
)