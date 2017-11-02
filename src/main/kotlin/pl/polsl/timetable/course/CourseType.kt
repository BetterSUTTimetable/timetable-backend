package pl.polsl.timetable.course

enum class CourseType {
    Lecture, Laboratory, Exercises, Project, Seminar, Unknown;

    companion object {
        fun shortNames(): Map<String, CourseType> = mapOf(
                "wyk" to Lecture,
                "lab" to Laboratory,
                "ćw" to Exercises,
                "proj" to Project,
                "sem" to Seminar
        )
    }
}