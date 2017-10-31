package pl.polsl.timetable.`class`

import org.springframework.data.jpa.repository.JpaRepository

interface ClassroomRepository: JpaRepository<JpaClassroom, Long> {
}