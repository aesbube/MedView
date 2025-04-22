package app.medview.service.impl

import app.medview.domain.User
import app.medview.domain.dto.JwtResponse
import app.medview.domain.dto.LoginRequest
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.SignupRequest
import app.medview.repository.UserRepository
import app.medview.security.JwtTokenProvider
import app.medview.service.AuthService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) : AuthService {
    override fun authenticateUser(loginRequest: LoginRequest): JwtResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtTokenProvider.generateToken(authentication)
        val user = userRepository.findByUsername(loginRequest.username)!!

        return JwtResponse(
            token = jwt,
            id = user.id,
            username = user.username
        )
    }

    override fun registerUser(signupRequest: SignupRequest): MessageResponse {
        if (userRepository.existsByUsername(signupRequest.username)) {
            return MessageResponse("Error: Username is already taken!")
        }

        if (userRepository.existsByEmail(signupRequest.email)) {
            return MessageResponse("Error: Email is already in use!")
        }

        val user = User(
            username = signupRequest.username,
            email = signupRequest.email,
            password = passwordEncoder.encode(signupRequest.password)
        )

        userRepository.save(user)
        return MessageResponse("User registered successfully!")
    }
}