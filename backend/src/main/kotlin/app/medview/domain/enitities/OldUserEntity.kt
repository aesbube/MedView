package app.medview.domain.enitities

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class OldUserEntity(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "username", nullable = false, unique = true)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "surname", nullable = false)
    val surname: String,

    @Column(name = "email")
    val email: String? = null,

    @Column(name = "phone")
    val phone: String? = null,

    @Column(name = "role_id", nullable = false)
    val roleId: Int,

    @Column(name = "entity_id")
    val entityId: Int? = null,

    @Column(name = "enabled")
    val enabled: Boolean? = true

)
