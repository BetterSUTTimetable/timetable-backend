package pl.polsl.timetable.course.room

interface ClassroomService {
    fun findOrCreate(classroom: Classroom): JpaClassroom
}