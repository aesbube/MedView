package app.medview.service.impl

import app.medview.domain.Role
import app.medview.domain.User
import app.medview.domain.dto.JwtResponse
import app.medview.domain.dto.LoginRequest
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.SignupRequest
import app.medview.domain.users.Doctor
import app.medview.domain.users.Patient
import app.medview.domain.users.Pharmacist
import app.medview.domain.users.Specialist
import app.medview.repository.DoctorRepository
import app.medview.repository.PatientRepository
import app.medview.repository.PharmacistRepository
import app.medview.repository.SpecialistRepository
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
    private val doctorRepository: DoctorRepository,
    private val patientRepository: PatientRepository,
    private val pharmacistRepository: PharmacistRepository,
    private val specialistRepository: SpecialistRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) : AuthService {
    val logger = org.slf4j.LoggerFactory.getLogger(AuthServiceImpl::class.java)
    override fun authenticateUser(loginRequest: LoginRequest): JwtResponse {
        logger.info("Authenticating user: ${loginRequest.username} with password: ${loginRequest.password}")
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        logger.info("Authentication successful for user: ${loginRequest.username}")

        SecurityContextHolder.getContext().authentication = authentication
        logger.info(SecurityContextHolder.getContext().authentication.name)
        logger.info("Generating JWT token for user: ${loginRequest.username}")
        val jwt = jwtTokenProvider.generateToken(authentication)
        logger.info("JWT token generated for user: ${loginRequest.username} and the token is $jwt")
        val user = userRepository.findByUsername(loginRequest.username)!!
        logger.info("User details retrieved for user: ${loginRequest.username}")
        return JwtResponse(
            token = jwt,
            id = user.id,
            username = user.username,
            role = user.role,
        )
    }

    override fun registerUser(signupRequest: SignupRequest): MessageResponse {
        if (userRepository.existsByUsername(signupRequest.username)) {
            return MessageResponse("Error: Username is already taken!")
        }

        if (userRepository.existsByEmail(signupRequest.email)) {
            return MessageResponse("Error: Email is already in use!")
        }

        val encodedPassword = passwordEncoder.encode(signupRequest.password)

        when (signupRequest.role) {
            Role.DOCTOR -> {
                val doctor = Doctor(
                    username = signupRequest.username,
                    email = signupRequest.email,
                    password = encodedPassword
                )
                doctorRepository.save(doctor)
            }
            Role.PATIENT -> {
                val patient = Patient(
                    username = signupRequest.username,
                    email = signupRequest.email,
                    password = encodedPassword
                )
                patientRepository.save(patient)
            }
            Role.PHARMACIST -> {
                val pharmacist = Pharmacist(
                    username = signupRequest.username,
                    email = signupRequest.email,
                    password = encodedPassword
                )
                pharmacistRepository.save(pharmacist)
            }
            Role.SPECIALIST -> {
                val specialist = Specialist(
                    username = signupRequest.username,
                    email = signupRequest.email,
                    password = encodedPassword
                )
                specialistRepository.save(specialist)
            }
            else -> {
                val user = User(
                    username = signupRequest.username,
                    email = signupRequest.email,
                    password = encodedPassword,
                    role = signupRequest.role ?: Role.PATIENT
                )
                userRepository.save(user)
            }
        }
        return MessageResponse("User registered successfully!")
    }
}