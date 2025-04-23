package app.medview.service.impl

import app.medview.domain.User
import app.medview.repository.UserRepository
import app.medview.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    val logger = org.slf4j.LoggerFactory.getLogger(UserServiceImpl::class.java)
    override fun getCurrentUser(): User {
        logger.info(SecurityContextHolder.getContext().authentication.name)
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name

        return userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
    }

    override fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow {
            UsernameNotFoundException("User not found with id: $id")
        }
    }
}