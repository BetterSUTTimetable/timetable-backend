package pl.polsl.timetable.course.filter

import org.junit.Assert
import org.junit.Test
import pl.polsl.timetable.course.Course
import pl.polsl.timetable.course.CourseType
import pl.polsl.timetable.course.DefaultCourse
import pl.polsl.timetable.course.name.DefaultCourseName
import java.time.Duration
import java.time.Instant
import java.time.LocalTime

class DataBasedCourseFilterTest {
    val duration = Duration.ofMinutes(90)
    val name = DefaultCourseName("NAME", "N")
    val type = CourseType.Exercises

    private fun createOddCourse(): Course {
        return DefaultCourse(
                beginTime = Instant.parse("2017-01-01T07:30:00.00Z"),
                duration = duration,
                courseType = type,
                classrooms = emptySet(),
                lecturers = emptySet(),
                name = name
        )
    }

    private fun createEvenCourse(): Course {
        return DefaultCourse(
                beginTime = Instant.parse("2017-01-08T07:30:00.00Z"),
                duration = duration,
                courseType = type,
                classrooms = emptySet(),
                lecturers = emptySet(),
                name = name
        )
    }

    private fun createFilterData(courseType: CourseType, name: String, week: Week, time: LocalTime, duration: Duration): CourseFilterData {
        return object : CourseFilterData{
            override val courseType = courseType
            override val fullCourseName = name
            override val week: Week = week
            override val time: LocalTime = time
            override val duration: Duration = duration
        }
    }

    @Test
    fun `a valid filter every week should return true for odd and even course`() {
        val filterData = createFilterData(
                courseType = type,
                name = name.fullName,
                week = Week.EveryWeek,
                time = LocalTime.of(7, 30),
                duration = duration
        )

        val filter = DataBasedCourseFilter(filterData)

        Assert.assertTrue(filter(createEvenCourse()))
        Assert.assertTrue(filter(createOddCourse()))
    }


    @Test
    fun `a valid filter even week should return true for even week course and false for odd week course`() {
        val filterData = createFilterData(
                courseType = type,
                name = name.fullName,
                week = Week.EvenWeek,
                time = LocalTime.of(7, 30),
                duration = duration
        )

        val filter = DataBasedCourseFilter(filterData)

        Assert.assertTrue(filter(createEvenCourse()))
        Assert.assertFalse(filter(createOddCourse()))
    }

    @Test
    fun `a valid filter for odd week should return true for odd week course and false for even week course`() {
        val filterData = createFilterData(
                courseType = type,
                name = name.fullName,
                week = Week.OddWeek,
                time = LocalTime.of(7, 30),
                duration = duration
        )

        val filter = DataBasedCourseFilter(filterData)

        Assert.assertFalse(filter(createEvenCourse()))
        Assert.assertTrue(filter(createOddCourse()))
    }

    @Test
    fun `filter with non-matching type should return false`() {
        val filterData = createFilterData(
                courseType = CourseType.Laboratory,
                name = name.fullName,
                week = Week.EveryWeek,
                time = LocalTime.of(7, 30),
                duration = duration
        )

        val filter = DataBasedCourseFilter(filterData)

        Assert.assertFalse(filter(createEvenCourse()))
        Assert.assertFalse(filter(createOddCourse()))
    }

    @Test
    fun `filter with non-matching name should return false`() {
        val filterData = createFilterData(
                courseType = type,
                name = "WRONG NAME",
                week = Week.EveryWeek,
                time = LocalTime.of(7, 30),
                duration = duration
        )

        val filter = DataBasedCourseFilter(filterData)

        Assert.assertFalse(filter(createEvenCourse()))
        Assert.assertFalse(filter(createOddCourse()))
    }

    @Test
    fun `filter with non-matching time should return false`() {
        val filterData = createFilterData(
                courseType = type,
                name = name.fullName,
                week = Week.EveryWeek,
                time = LocalTime.of(7, 15),
                duration = duration
        )

        val filter = DataBasedCourseFilter(filterData)

        Assert.assertFalse(filter(createEvenCourse()))
        Assert.assertFalse(filter(createOddCourse()))
    }

    @Test
    fun `filter with non-matching duration should return false`() {
        val filterData = createFilterData(
                courseType = type,
                name = name.fullName,
                week = Week.EveryWeek,
                time = LocalTime.of(7, 30),
                duration = Duration.ofMinutes(60)
        )

        val filter = DataBasedCourseFilter(filterData)

        Assert.assertFalse(filter(createEvenCourse()))
        Assert.assertFalse(filter(createOddCourse()))
    }
}