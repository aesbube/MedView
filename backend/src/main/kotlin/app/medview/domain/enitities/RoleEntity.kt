package app.medview.domain.enitities

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    val roleId: Int? = null,

    @Column(name = "role_name", nullable = false, unique = true)
    val roleName: String
)