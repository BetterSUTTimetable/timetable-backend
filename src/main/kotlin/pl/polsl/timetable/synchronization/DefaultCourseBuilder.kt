package pl.polsl.timetable.synchronization

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.polsl.timetable.course.*
import pl.polsl.timetable.course.lecturer.Lecturer
import pl.polsl.timetable.course.name.DefaultCourseName
import pl.polsl.timetable.course.room.Classroom
import pl.polsl.timetable.synchronization.parser.IcsEvent
import pl.polsl.timetable.synchronization.parser.IcsFileParser
import pl.polsl.timetable.synchronization.scraper.TimetablePage
import java.time.Duration

@Component
class DefaultCourseBuilder(
        @Autowired
        private val parser: IcsFileParser
): CoursesBuilder {
    override fun build(timetablePage: TimetablePage): List<Course> {
        timetablePage.icsFile.use {
            val events = parser.parse(it)
            return events
                    .map { createCourse(timetablePage, it) }
                    .toList()
        }
    }

    private fun createCourse(timetablePage: TimetablePage, event: IcsEvent): Course {
        val (courseType, splitSummary) = splitSummary(event.summary)

        val (lecturers, afterLecturersExtraction) = extractLecturers(timetablePage, splitSummary.last())

        val (classrooms, afterAllExtractions) = extractClassrooms(timetablePage, afterLecturersExtraction)

        val shortCourseName = if (splitSummary.size == 2) splitSummary.first() else afterAllExtractions

        val longCourseName = timetablePage.classNames[shortCourseName] ?: shortCourseName

        val courseName = DefaultCourseName(shortName = shortCourseName, fullName = longCourseName)

        val beginTime = event.start

        val duration = Duration.between(beginTime, event.end)

        return DefaultCourse(
                courseType = courseType,
                classrooms = classrooms,
                lecturers = lecturers,
                name = courseName,
                beginTime = beginTime,
                duration = duration
        )
    }

    private fun splitSummary(summary: String): Pair<CourseType, List<String>> {
        for ((name, type) in CourseType.shortNames()) {
            val regex = Regex("\\s(\\Q$name\\E)(\\s|\$)")
            if (summary.contains(regex)) {
                return type to summary.split(regex, 2)
            }
        }
        return CourseType.Unknown to listOf(summary)
    }

    private fun extractLecturers(timetablePage: TimetablePage, potentialLecturers: String): Pair<Set<Lecturer>, String> {
        val lecturers = mutableSetOf<Lecturer>()
        var processedString = potentialLecturers
        for (lecturer in timetablePage.lecturers) {
            val regex = Regex("\\b(\\Q${lecturer.shortName}\\E)\\b")
            if (processedString.contains(regex)) {
                processedString = processedString.replace(regex, "")
                lecturers.add(lecturer)
            }
        }
        return lecturers to processedString
    }

    private fun extractClassrooms(timetablePage: TimetablePage, potentialClassrooms: String): Pair<Set<Classroom>, String> {
        val classrooms = mutableSetOf<Classroom>()
        var processedString = potentialClassrooms
        for (classroom in timetablePage.classrooms) {
            val regex = Regex("\\b(\\Q${classroom.room}\\E)\\b")
            if (processedString.contains(regex)) {
                processedString = processedString.replace(regex, "")
                classrooms.add(classroom)
            }
        }
        return classrooms to processedString
    }
}