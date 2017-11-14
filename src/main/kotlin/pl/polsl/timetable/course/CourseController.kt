package pl.polsl.timetable.course

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.Clock
import java.time.Instant
import java.util.*
import javax.websocket.server.PathParam

@RestController
class CourseController(
        @Autowired
        private val courseService: CourseService
) {
    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/category/{id}/courses")
    fun courses(
            @PathVariable id: Long,
            @RequestParam(name = "from", required = true) from: Instant,
            @RequestParam(name = "to", required = true) to: Instant
    ): List<Course> {
        return courseService.coursesBetween(id, from..to)
    }
}