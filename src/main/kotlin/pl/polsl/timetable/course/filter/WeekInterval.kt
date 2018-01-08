package pl.polsl.timetable.course.filter

enum class WeekInterval(
    val names: Map<String, String>
) {
    EveryWeek(mapOf("pl" to "Co tydzień")),
    EveryTwoWeeks(mapOf("pl" to "Co drugi tydzień"));

    companion object {
       fun translations(): Map<WeekInterval, Map<String, String>> {
           return values().map { it to it.names }.toMap()
       }
    }
}