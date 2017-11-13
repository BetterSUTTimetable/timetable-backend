package pl.polsl.timetable.course.name

interface CourseNameService {
    fun findOrCreate(courseName: CourseName): JpaCourseName
}