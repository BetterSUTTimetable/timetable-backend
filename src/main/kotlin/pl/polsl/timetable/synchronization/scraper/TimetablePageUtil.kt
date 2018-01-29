package pl.polsl.timetable.synchronization.scraper

import org.jsoup.nodes.Document

fun hasCourses(timetable: Document): Boolean {
    return timetable
            .select(".data a")
            .firstOrNull {
                it.text().trim() == "plan.ics - dane z zajęciami dla kalendarzy MS Outlook, Kalendarz Google"
            } != null
}

fun findGroupName(timetable: Document): String {
    val text = timetable
            .select("div .title")
            .map { it.text() }
            .firstOrNull { it.startsWith("Plan zajęć") }
            ?: throw RuntimeException("Cannot find group name in timetable!")

    return Regex("Plan zajęć - (.*), semestr")
            .find(text)
            ?.groups
            ?.get(1)
            ?.value
            ?.trim()
            ?: throw RuntimeException("Cannot find group name in timetable!")
}