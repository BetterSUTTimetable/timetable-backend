package pl.polsl.timetable.course

import org.springframework.data.jpa.repository.JpaRepository

interface ClassroomRepository: JpaRepository<JpaClassroom, Long> {
}