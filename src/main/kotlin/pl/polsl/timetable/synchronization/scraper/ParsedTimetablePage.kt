package pl.polsl.timetable.synchronization.scraper

import org.jsoup.nodes.Document
import pl.polsl.timetable.course.lecturer.Lecturer
import pl.polsl.timetable.course.room.Classroom
import pl.polsl.timetable.course.room.DefaultClassroom
import java.io.BufferedReader
import java.net.URL
import java.util.*

class ParsedTimetablePage(
        private val document: Document,
        private val icsFileFactory: (String) -> BufferedReader,
        private val lecturerFactory: (String, URL) -> Lecturer
): TimetablePage {
    override val groupName: String = {
        val text = document
                .select("div .title")
                .map { it.text() }
                .firstOrNull { it.startsWith("Plan zajęć") } ?: ""

        Regex("Plan zajęć - (.*), semestr")
                .find(text)
                ?.groups
                ?.get(1)
                ?.value
                ?.trim() ?: ""
    }()

    private val allCourseLinks = {
        document.select(".coursediv a")
                .map {
                    it.absUrl("href") to it
                }
                .filter { (first, _) -> first != null }
    }()

    override val classNames: Map<String, String> = {
        val legend = document.select(".data").first()
        val shortNames = legend.select("strong")

        shortNames
                .map {
                    val shortName = it.text().trim()
                    val longName = it.nextSibling().toString().trim(' ', '-', '\t')
                    shortName to longName
                }
                .toMap()
    }()

    override val lecturers: Set<Lecturer> by lazy{
        allCourseLinks
                .filter { (url, _) ->
                    url.contains("type=10")
                }
                .map{ (url, element) ->
                    lecturerFactory(element.text().trim(), URL(url))
                }
                .toSet()
    }

    override val classrooms: Set<Classroom> = {
        allCourseLinks
                .filter { (url, _) ->
                    url.contains("type=20")
                }
                .map{ (_, second) ->
                    DefaultClassroom(second.text().trim())
                }
                .toSet()
    }()

    override val icsFile: Optional<BufferedReader> by lazy {
        //TODO: fix this horror
        val link = document
                .select(".data a")
                .firstOrNull { it.text().trim() == "plan.ics - dane z zajęciami dla kalendarzy MS Outlook, Kalendarz Google"}
                ?.absUrl("href")

        if (link != null) {
            Optional.of(icsFileFactory(link))
        } else {
            Optional.empty()
        }
    }
}