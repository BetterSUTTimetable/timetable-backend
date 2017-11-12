package pl.polsl.timetable.synchronization.scraper

import org.jsoup.nodes.Document
import pl.polsl.timetable.course.*
import java.net.URL

class ParsedTimetablePage(
        private val document: Document,
        private val icsFileFactory: (String) -> IcsFile,
        private val lecturerFactory: (String, URL) -> Lecturer
): TimetablePage {
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

    override val icsFile: IcsFile by lazy {
        val link = document
                .select(".data a")
                .filter { it.text() == "plan.ics - dane z zajęciami dla kalendarzy MS Outlook, Kalendarz Google"}
                .first()
                .absUrl("href")

        icsFileFactory(link)
    }
}