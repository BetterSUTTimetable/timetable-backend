package pl.polsl.timetable.synchronization.scraper

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getAll
import com.github.michaelbull.result.map
import org.jsoup.nodes.Document
import pl.polsl.timetable.course.lecturer.Lecturer
import pl.polsl.timetable.course.room.Classroom
import pl.polsl.timetable.course.room.DefaultClassroom
import java.io.BufferedReader
import java.net.URL
import java.util.*

class ParsedTimetablePage(
        document: Document,
        icsFileFactory: (String) -> BufferedReader,
        private val lecturerFactory: (String, URL) -> Result<Lecturer, Throwable>
): TimetablePage {
    private val allCourseLinks = {
        document.select(".coursediv a")
                .map {
                    it.absUrl("href") to it
                }
                .filter { (first, _) -> first != null }
    }()

    override val groupName: String = {
        val text = document
                .select("div .title")
                .map { it.text() }
                .firstOrNull { it.startsWith("Plan zajęć") }
                ?: throw RuntimeException("Cannot find group name in timetable!")

        Regex("Plan zajęć - (.*), semestr")
                .find(text)
                ?.groups
                ?.get(1)
                ?.value
                ?.trim()
                ?: throw RuntimeException("Cannot find group name in timetable!")
    }()

    override val classNames: Map<String, String> = {
        val legend = document.select(".data").firstOrNull()
                ?: throw RuntimeException("Timetable for $groupName doesn't contain class names!")
        val shortNames = legend.select("strong")

        shortNames
                .map {
                    val shortName = it.text().trim()
                    val longName = it.nextSibling().toString().trim(' ', '-', '\t')
                    shortName to longName
                }
                .toMap()
    }()

    override val lecturers: Set<Lecturer> by lazy {
        allCourseLinks
                .filter { (url, _) ->
                    url.contains("type=10")
                }
                .map{ (url, element) ->
                    lecturerFactory(element.text().trim(), URL(url))
                }
                .getAll()
                .toSet()
    }

    override val classrooms: Set<Classroom> =
        allCourseLinks
                .filter { (url, _) ->
                    url.contains("type=20")
                }
                .map{ (_, second) ->
                    DefaultClassroom(second.text().trim())
                }
                .toSet()

    override val icsFile: BufferedReader by lazy {
        icsFileFactory(
                icsFileLink
        )
    }

    private val icsFileLink = document
            .select(".data a")
            .firstOrNull { it.text().trim() == "plan.ics - dane z zajęciami dla kalendarzy MS Outlook, Kalendarz Google" }
            ?.absUrl("href") ?: throw RuntimeException("`$groupName` timetable doesn't contain ICS link!")
}