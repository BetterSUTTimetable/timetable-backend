package pl.polsl.timetable.course

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pl.polsl.timetable.course.category.CategoryRepository
import pl.polsl.timetable.course.category.JpaCategory
import pl.polsl.timetable.course.lecturer.JpaLecturer
import pl.polsl.timetable.course.lecturer.LecturerRepository
import pl.polsl.timetable.course.name.CourseNameRepository
import pl.polsl.timetable.course.name.JpaCourseName
import pl.polsl.timetable.course.room.ClassroomRepository
import pl.polsl.timetable.course.room.JpaClassroom
import java.time.Duration
import java.time.Instant

@RunWith(SpringRunner::class)
@SpringBootTest
class RepositoriesTests {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var courseNameRepository: CourseNameRepository

    @Autowired
    lateinit var classroomRepository: ClassroomRepository

    @Autowired
    lateinit var lecturerRepository: LecturerRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Before
    fun setUp() {
        val lecturer = JpaLecturer("Adam Domański", "AD")
        val classroom = JpaClassroom("Aula A")
        val className = JpaCourseName("Praktyka Programowania Python", "PPP")
        val category = JpaCategory("Informatyka, sem. 1", null)
        val classEntry = JpaCourse.create(className, CourseType.Lecture, Instant.ofEpochMilli(123214L), Duration.ofMinutes(90L), setOf(classroom), setOf(lecturer), category);

        val className2 = JpaCourseName("Tworzenie Aplikacji Internetowych", "TAI")
        val classEntry2 = JpaCourse.create(className2, CourseType.Lecture, Instant.ofEpochMilli(123214L), Duration.ofMinutes(90L), setOf(classroom), setOf(lecturer), category)

        categoryRepository.save(category)
        lecturerRepository.save(lecturer)
        classroomRepository.save(classroom)
        courseNameRepository.save(className)
        courseRepository.save(classEntry)
        courseNameRepository.save(className2)
        courseRepository.save(classEntry2)
    }

    @Test
    fun readingWorks() {
        Assert.assertEquals(2, courseRepository.findAll().size)

        val names = courseRepository.findAll().map { it.name.fullName }.toSet()

        Assert.assertTrue(names.contains("Praktyka Programowania Python"))
        Assert.assertTrue(names.contains("Tworzenie Aplikacji Internetowych"))
    }

    @Test
    fun findingInCategoryWorks() {
        val category = categoryRepository.findAll().first()
        val courses = courseRepository.findByCategoryOrderByBeginTimestampDesc(category)
        Assert.assertEquals(2, courses.size)

        val names = courses.map { it.name.fullName }.toSet()

        Assert.assertTrue(names.contains("Praktyka Programowania Python"))
        Assert.assertTrue(names.contains("Tworzenie Aplikacji Internetowych"))
    }


    @After
    fun tearDown() {
        courseRepository.deleteAll()
        categoryRepository.deleteAll()
        lecturerRepository.deleteAll()
        classroomRepository.deleteAll()
        courseNameRepository.deleteAll()
    }

}