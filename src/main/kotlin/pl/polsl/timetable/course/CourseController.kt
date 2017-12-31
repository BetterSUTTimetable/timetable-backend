package pl.polsl.timetable.course

import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.Clock
import java.time.Instant
import java.util.*
import javax.websocket.server.PathParam

@RestController
class CourseController(
        private val courseService: CourseService
) {
    @RequestMapping(method = [RequestMethod.GET], value = ["/category/{id}/courses"])
    fun courses(
            @PathVariable
            id: Long,

            @ApiParam(
                    value = "Date and time in ISO 8601 format acceptable " +
                            "by `java.time.Instant.parse` e.g. `2017-11-15T08:00:00Z`",
                    required = true
            )
            @RequestParam(name = "from", required = true)
            from: Instant,

            @ApiParam(
                    value = "Date and time in ISO 8601 format acceptable " +
                            "by `java.time.Instant.parse` e.g. `2017-11-15T10:30:00Z`",
                    required = true
            )
            @RequestParam(name = "to", required = true)
            to: Instant
    ): List<Course> {
        return courseService.coursesBetween(id, from..to)
    }
}