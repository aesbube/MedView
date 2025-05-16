package app.medview.service

import app.medview.domain.dto.DiagnosisDto

interface OpenAiService {
    fun simplify(refNumber: String) : DiagnosisDto?
}