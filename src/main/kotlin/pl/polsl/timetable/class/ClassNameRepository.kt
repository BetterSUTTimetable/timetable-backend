package pl.polsl.timetable.`class`

import org.springframework.data.jpa.repository.JpaRepository

interface ClassNameRepository: JpaRepository<JpaClassName, Long> {
}