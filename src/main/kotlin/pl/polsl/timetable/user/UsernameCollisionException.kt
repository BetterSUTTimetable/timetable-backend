package pl.polsl.timetable.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT)
class UsernameCollisionException(message: String) : RuntimeException(message)