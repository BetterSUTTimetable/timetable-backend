package pl.polsl.timetable.course

import org.springframework.data.jpa.repository.JpaRepository

interface LecturerRepository: JpaRepository<JpaLecturer, Long>