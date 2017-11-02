package pl.polsl.timetable.scraper

import org.jsoup.nodes.Document
import pl.polsl.timetable.`class`.ClassName
import pl.polsl.timetable.`class`.JpaClassName

class ParsedTimetablePage(private val document: Document): TimetablePage {
    override val url: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val classNames: Map<String, String> by lazy {
        val legend = document.select(".data").first()
        val subjects = legend.select("strong")

        subjects
                .map {
                    val shortName = it.text()
                    val longName = it.nextSibling().toString().trim(' ','-','\t')
                    shortName to longName
                }
                .toMap()
    }
}