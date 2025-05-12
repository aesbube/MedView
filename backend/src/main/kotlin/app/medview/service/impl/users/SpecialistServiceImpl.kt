package app.medview.service.impl.users

import app.medview.domain.Diagnosis
import app.medview.domain.Role
import app.medview.domain.Schedule
import app.medview.domain.converter.SpecialistEntityToDtoConverter
import app.medview.domain.dto.AppointmentDto
import app.medview.domain.dto.DiagnosisDto
import app.medview.domain.dto.FreeAppointmentDto
import app.medview.domain.dto.MessageResponse
import app.medview.domain.dto.users.SpecialistDto
import app.medview.domain.users.Specialist
import app.medview.repository.AppointmentRepository
import app.medview.repository.DiagnosisRepository
import app.medview.repository.PatientRepository
import app.medview.repository.ScheduleRepository
import app.medview.repository.SpecialistRepository
import app.medview.service.AppointmentService
import app.medview.service.users.SpecialistService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import kotlin.collections.forEach

@Service
class SpecialistServiceImpl(
    private val specialistRepository: SpecialistRepository,
    private val scheduleRepository: ScheduleRepository,
    private val patientRepository: PatientRepository,
    private val appointmentRepository: AppointmentRepository,
    private val diagnosisRepository: DiagnosisRepository,
    private val appointmentService: AppointmentService,
    private val specialistEntityToDtoConverter: SpecialistEntityToDtoConverter,
) : SpecialistService {
    val logger = org.slf4j.LoggerFactory.getLogger(SpecialistServiceImpl::class.java)
    override fun getAllSpecialists(): List<SpecialistDto> {
        return specialistRepository.findAll().map { specialistEntityToDtoConverter.convert(it) }
    }

    override fun getSpecialistsByUsername(username: String): List<Specialist> {
        return specialistRepository.findByUsernameContainingIgnoreCase(username)
    }

    override fun getSpecialistById(id: Long): Specialist {
        return specialistRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Specialist not found with id: $id")
        }
    }

    override fun getSpecialistScheduleById(id: Long): Schedule {
        val schedule = scheduleRepository.findBySpecialistId(id)
        return schedule
    }

    override fun addDetailsToSpecialist(specialistDto: SpecialistDto): MessageResponse {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name

        val specialist = specialistRepository.findByUsername(username)
            ?: throw RuntimeException("Specialist not found with username: $username")

        if (specialist.role != Role.SPECIALIST) {
            throw RuntimeException("User is not a specialist")
        }

        specialist.specialty = specialistDto.specialty ?: specialist.specialty
        specialist.licenseNumber = specialistDto.licenseNumber ?: specialist.licenseNumber
        specialist.yearsOfExperience = specialistDto.yearsOfExperience ?: specialist.yearsOfExperience
        specialist.name = specialistDto.name ?: specialist.name
        specialist.surname = specialistDto.surname ?: specialist.surname
        specialist.phone = specialistDto.phone ?: specialist.phone
        specialist.address = specialistDto.address ?: specialist.address
        specialist.birthDate = specialistDto.birthDate ?: specialist.birthDate

        specialistRepository.save(specialist)
        return MessageResponse("Specialist details added successfully")
    }

    override fun setFreeAppointments(appointments: List<FreeAppointmentDto>): MessageResponse {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name
        logger.info("Setting free appointments for specialist: $username")
        val specialist = specialistRepository.findByUsername(username)
            ?: throw RuntimeException("Specialist not found with username: $username")
        logger.info("Specialist found with username: $username")
        val schedule = scheduleRepository.findBySpecialistId(specialist.id)
        logger.info("Schedule found for specialist: $username with id ${schedule.id}")
        val appointmentsInDb = appointmentRepository.findByScheduleId(schedule.id)
        val incomingSet = appointments.map { it.date to it.time }.toSet()

        for (appointment in appointmentsInDb) {
            val key = appointment.date to appointment.time
            if (key !in incomingSet) {
                logger.info("Deleting obsolete appointment: ${appointment.date} ${appointment.time}")
                appointmentService.deleteAppointment(appointment.id)
            }
        }
        appointments.forEach { appointment ->
            appointmentService.createFreeAppointment(appointment, schedule)
        }

        return MessageResponse("Appointments set successfully")
    }

    override fun getAppointments(): List<AppointmentDto> {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name
        val specialist = specialistRepository.findByUsername(username)
            ?: throw RuntimeException("Specialist not found with username: $username")
        val schedule = scheduleRepository.findBySpecialistId(specialist.id)

        return appointmentService.getAppointmentsByScheduleId(schedule.id)
    }

    override fun writeDiagnosis(
        appointmentId: Long,
        diagnosisDto: DiagnosisDto
    ): MessageResponse {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name

        val specialist = specialistRepository.findByUsername(username)
            ?: throw RuntimeException("Specialist not found with username: $username")

        val appointment = appointmentRepository.findById(appointmentId).orElseThrow {
            throw RuntimeException("Appointment not found with id: $appointmentId")
        }

        val patientId = appointment.patient?.id
            ?: throw RuntimeException("Patient not found in appointment with id: $appointmentId")

        val patient = patientRepository.findById(patientId).orElseThrow {
            throw RuntimeException("Patient not found with id: $patientId")
        }

        val diagnosis = Diagnosis(
            name = diagnosisDto.name,
            description = diagnosisDto.description,
            treatment = diagnosisDto.treatment,
            patient = patient,
            specialist = specialist,
            appointment = appointment
        )

        diagnosisRepository.save(diagnosis)
        return MessageResponse("Diagnosis written successfully")
    }

    override fun getOccupiedAppointments(): List<AppointmentDto> {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name
        val specialist = specialistRepository.findByUsername(username)
            ?: throw RuntimeException("Specialist not found with username: $username")
        val schedule = scheduleRepository.findBySpecialistId(specialist.id)

        return appointmentService.getOccupiedAppointmentsByScheduleId(schedule.id)
    }

    override fun getSpecialist(): SpecialistDto {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name
        val specialist = specialistRepository.findByUsername(username)
            ?: throw RuntimeException("Specialist not found with username: $username")

        return specialistEntityToDtoConverter.convert(specialist)
    }
}