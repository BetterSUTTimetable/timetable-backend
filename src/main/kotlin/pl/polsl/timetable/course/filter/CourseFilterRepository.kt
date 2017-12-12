package pl.polsl.timetable.course.filter

import org.springframework.data.jpa.repository.JpaRepository

interface CourseFilterRepository: JpaRepository<JpaCourseFilterData, Long> {
}