package pl.polsl.timetable.course

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
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

    @Before
    fun setUp() {
        val lecturer = JpaLecturer("Adam Domański", "AD")
        val classroom = JpaClassroom("Aula A")
        val className = JpaCourseName("Praktyka Programowania Python", "PPP")
        val classEntry = JpaCourse.create(className, CourseType.Lecture, Instant.ofEpochMilli(123214L), Duration.ofMinutes(90L), setOf(classroom), setOf(lecturer))

        val className2 = JpaCourseName("Tworzenie Aplikacji Internetowych", "TAI")
        val classEntry2 = JpaCourse.create(className2, CourseType.Lecture, Instant.ofEpochMilli(123214L), Duration.ofMinutes(90L), setOf(classroom), setOf(lecturer))

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

    @After
    fun tearDown() {
        courseRepository.deleteAll()
        lecturerRepository.deleteAll()
        classroomRepository.deleteAll()
        courseNameRepository.deleteAll()
    }

}