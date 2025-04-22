package app.medview.service

import app.medview.domain.User

interface UserService {
    fun getCurrentUser(): User
    fun getAllUsers(): List<User>
    fun getUserById(id: Long): User
}