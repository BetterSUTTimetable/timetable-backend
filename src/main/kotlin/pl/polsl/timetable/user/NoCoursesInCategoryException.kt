package pl.polsl.timetable.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class NoCoursesInCategoryException(message: String) : Exception(message)