package app.medview.repository

import app.medview.domain.Appointment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppointmentRepository : JpaRepository<Appointment, Long> {
    fun findByPatientId(patientId: Long): List<Appointment>
    fun findByAssigneeId(assigneeId: Long): List<Appointment>
    fun findByScheduleId(scheduleId: Long): List<Appointment>
    fun findByLocation(location: String): List<Appointment>
    fun findByScheduleSpecialistId(specialistId: Long): List<Appointment>
}