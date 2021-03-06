package pl.polsl.timetable.course.category

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class CategoryNotFoundException(id: Long): RuntimeException("Category $id not found!")