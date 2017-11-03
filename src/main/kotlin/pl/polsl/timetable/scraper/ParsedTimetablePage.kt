package pl.polsl.timetable.scraper

import org.jsoup.nodes.Document
import pl.polsl.timetable.course.*

class ParsedTimetablePage(private val document: Document): TimetablePage {
    private val allCourseLinks by lazy {
        document.select(".coursediv a")
                .map {
                    it.attr("href") to it
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

    override val lecturers: Map<String, Lecturer> by lazy {
        allCourseLinks
                .filter { (first, _) ->
                    first.contains("type=10")
                }
                .map{ (_, second) ->
                    second.text() to DefaultLecturer(second.text(), second.text())
                }
                .toMap()
    }

    override val classrooms: Map<String, Classroom> by lazy {
        allCourseLinks
                .filter { (first, _) ->
                    first.contains("type=20")
                }
                .map{ (_, second) ->
                    second.text() to DefaultClassroom(second.text())
                }
                .toMap()
    }
}