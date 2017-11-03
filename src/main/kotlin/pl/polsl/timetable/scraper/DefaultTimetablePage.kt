package pl.polsl.timetable.scraper

import pl.polsl.timetable.course.Classroom
import pl.polsl.timetable.course.Lecturer

class DefaultTimetablePage(url: String) : TimetablePage {
    override val classNames: Map<String, String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val lecturers: Map<String, Lecturer>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val classrooms: Map<String, Classroom>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}