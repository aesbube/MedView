package app.medview.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

class NullPrescriptionException : RuntimeException()