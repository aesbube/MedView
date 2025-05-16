package app.medview.domain.dto

import app.medview.domain.dto.users.SpecialistDto
import jakarta.validation.constraints.NotBlank

data class ScheduleDto(
    val id: Long,
    @field:NotBlank
    val specialist: SpecialistDto,
)
