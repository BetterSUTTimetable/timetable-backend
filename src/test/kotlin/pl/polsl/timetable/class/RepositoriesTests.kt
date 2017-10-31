package pl.polsl.timetable.`class`

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
    lateinit var classRepository: ClassRepository

    @Autowired
    lateinit var classNameRepository: ClassNameRepository

    @Autowired
    lateinit var classroomRepository: ClassroomRepository

    @Autowired
    lateinit var lecturerRepository: LecturerRepository

    @Before
    fun setUp() {
        val lecturer = JpaLecturer("Adam Domański", "AD")
        val classroom = JpaClassroom("Aula A")
        val className = JpaClassName("Praktyka Programowania Python", "PPP")
        val classEntry = JpaClass.create(className, ClassType.Lecture, Instant.ofEpochMilli(123214L), Duration.ofMinutes(90L), listOf(classroom), listOf(lecturer))



        val className2 = JpaClassName("Tworzenie Aplikacji Internetowych", "TAI")
        val classEntry2 = JpaClass.create(className2, ClassType.Lecture, Instant.ofEpochMilli(123214L), Duration.ofMinutes(90L), listOf(classroom), listOf(lecturer))

        lecturerRepository.save(lecturer)
        classroomRepository.save(classroom)
        classNameRepository.save(className)
        classRepository.save(classEntry)
        classNameRepository.save(className2)
        classRepository.save(classEntry2)
    }

    @Test
    fun readingWorks() {
        Assert.assertEquals(2, classRepository.findAll().size)

        val names = classRepository.findAll().map { it.name.fullName }.toSet()

        Assert.assertTrue(names.contains("Praktyka Programowania Python"))
        Assert.assertTrue(names.contains("Tworzenie Aplikacji Internetowych"))
    }

    @After
    fun tearDown() {
        classRepository.deleteAll()
        lecturerRepository.deleteAll()
        classroomRepository.deleteAll()
        classNameRepository.deleteAll()
    }

}