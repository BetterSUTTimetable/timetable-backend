package pl.polsl.timetable.synchronization.scraper

import org.jsoup.nodes.Document
import pl.polsl.timetable.course.lecturer.Lecturer
import java.util.*

class ParsedLecturer(document: Document, override val shortName: String): Lecturer {
    override val fullName = {
        val nameText = document
                .select("div .title")
                .filter {
                    it.text().startsWith("Plan zajęć - ")
                }
                .first()
                .text()
        Regex("Plan zajęć - (.*), semestr")
                .find(nameText)
                ?.groups
                ?.get(1)
                ?.value
                ?.trim() ?: shortName
    }()

    override fun equals(other: Any?): Boolean
            = other is ParsedLecturer && other.shortName == shortName && other.fullName == fullName

    override fun hashCode() = Objects.hash(shortName, fullName)
}