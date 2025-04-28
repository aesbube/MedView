package app.medview.repository

import app.medview.domain.Schedule
import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository : JpaRepository<Schedule, Long> {
    fun findBySpecialistId(specialistId: Long): List<Schedule>
    fun findBySpecialistUsername(username: String): List<Schedule>
}