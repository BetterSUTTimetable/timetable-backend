package pl.polsl.timetable.scraper

interface TimetablePage {
    val url: String
    val classNames: Map<String, String>
}