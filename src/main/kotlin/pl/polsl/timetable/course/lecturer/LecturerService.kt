package pl.polsl.timetable.course.lecturer

interface LecturerService {
    fun findOrCreate(lecturer: Lecturer): JpaLecturer
}