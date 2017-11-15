package pl.polsl.timetable.synchronization.scraper

import org.jsoup.nodes.Document
import pl.polsl.timetable.course.lecturer.Lecturer
import pl.polsl.timetable.course.room.Classroom
import pl.polsl.timetable.course.room.DefaultClassroom
import java.io.BufferedReader
import java.net.URL

class ParsedTimetablePage(
        private val document: Document,
        private val icsFileFactory: (String) -> BufferedReader,
        private val lecturerFactory: (String, URL) -> Lecturer
): TimetablePage {
    override val groupName: String by lazy {
        val text = document
                .select("div .title")
                .map { it.text() }
                .firstOrNull { it.startsWith("Plan zajęć") } ?: ""

        Regex("Plan zajęć - (.*), semestr")
                .find(text)
                ?.groups
                ?.get(1)
                ?.value ?: ""
    }

    private val allCourseLinks by lazy {
        document.select(".coursediv a")
                .map {
                    it.absUrl("href") to it
                }
                .filter { (first, _) -> first != null }
    }

    override val classNames: Map<String, String> by lazy {
        val legend = document.select(".data").first()
        val shortNames = legend.select("strong")

        shortNames
                .map {
                    val shortName = it.text()
                    val longName = it.nextSibling().toString().trim(' ', '-', '\t')
                    shortName to longName
                }
                .toMap()
    }

    override val lecturers: Set<Lecturer> by lazy {
        allCourseLinks
                .filter { (url, _) ->
                    url.contains("type=10")
                }
                .map{ (url, element) ->
                    lecturerFactory(element.text(), URL(url))
                }
                .toSet()
    }

    override val classrooms: Set<Classroom> by lazy {
        allCourseLinks
                .filter { (url, _) ->
                    url.contains("type=20")
                }
                .map{ (_, second) ->
                    DefaultClassroom(second.text())
                }
                .toSet()
    }

    override val icsFile: BufferedReader by lazy {
        val link = document
                .select(".data a")
                .first { it.text() == "plan.ics - dane z zajęciami dla kalendarzy MS Outlook, Kalendarz Google"}
                .absUrl("href")

        icsFileFactory(link)
    }
}