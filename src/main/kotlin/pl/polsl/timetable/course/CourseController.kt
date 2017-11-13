package pl.polsl.timetable.course

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.Clock
import java.time.Instant
import java.util.*
import javax.websocket.server.PathParam

@RestController
class CourseController {
    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/category/{id}/courses")
    fun courses(
            @PathVariable id: Long,
            @RequestParam(name = "from", required = false) from: Optional<Instant>,
            @RequestParam(name = "to", required = false) to: Optional<Instant>
    ): List<Course> {
        return emptyList()
    }
}