import org.jsoup.Jsoup
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import pl.polsl.timetable.course.lecturer.DefaultLecturer
import pl.polsl.timetable.synchronization.scraper.ParsedTimetablePage
import pl.polsl.timetable.synchronization.scraper.TimetablePage
import java.io.BufferedReader
import java.io.File
import java.net.URL

class ParsedTimetableOnlineTest {
    private val document = Jsoup.connect("https://plan.polsl.pl/plan.php?type=0&id=1417&winW=1584&winH=818&loadBG=000000").get()
    private val timetable: TimetablePage = ParsedTimetablePage(document, this::createIcsFile, this::createLecturer)

    @Test
    fun groupNameTest() {
        Assert.assertEquals("Ar.D.1st.Ir.3 gr /", timetable.groupName)
    }

    @Test
    fun sampleClassNamesTest() {
        val expectedNames = mapOf(
                "Hist. arch." to "Historia architektury",
                "JO / Foreign language" to "Język Obcy/ Foreign language, Sale SPNJO"
        )

        for ((key, value) in expectedNames) {
            Assert.assertEquals(
                    "$key: $value no found in ${timetable.classNames}",
                    value,
                    timetable.classNames[key]
            )
        }
    }

    @Test
    fun sampleClassroomsTest() {
        val excpectedRooms = setOf(
                "Arch_X 110 komp.",
                "Ar 305"
        )

        for (value in excpectedRooms) {
            Assert.assertTrue(
                    "Classroom $value: no found in ${timetable.classrooms}",
                    timetable.classrooms.any { it.room == value }
            )
        }
    }

    @Test
    fun sampleLecturersTest() {
        val excpectedLectureres = setOf(
                "drKrzGer",
                "AnG"
        )

        for (value in excpectedLectureres) {
            Assert.assertTrue(
                    "Lecturer $value: no found in ${timetable.lecturers}",
                    timetable.lecturers.any { it.shortName == value }
            )
        }
    }

    @Test
    fun icsTest() {
        //just to create it - assertion called indirectely
        val file = timetable.icsFile
    }

    private fun createIcsFile(url: String): BufferedReader {
        Assert.assertEquals("https://plan.polsl.pl/plan.php?type=0&id=1417&cvsfile=true&wd=1", url)
        return Mockito.mock(BufferedReader::class.java)
    }

    private fun createLecturer(name: String, url: URL) = DefaultLecturer(name, name)
}