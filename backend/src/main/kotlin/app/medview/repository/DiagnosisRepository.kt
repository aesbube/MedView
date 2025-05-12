package app.medview.repository

import app.medview.domain.Diagnosis
import app.medview.domain.users.Specialist
import org.springframework.data.jpa.repository.JpaRepository

interface DiagnosisRepository : JpaRepository<Diagnosis, Long> {
    fun findDiagnosisByPatientId(patientId: Long): List<Diagnosis>
    fun findDiagnosisByAppointmentId(appointmentId: Long): Diagnosis?
    fun findDiagnosisBySpecialistId(specialistId: Long): List<Diagnosis>
}