package pl.polsl.timetable.synchronization.parser

import org.junit.Assert
import org.junit.Test
import pl.polsl.timetable.synchronization.scraper.FetchingIcsFile
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.net.URL

class FetchingIcsFileTest {
    val fileURL = ParserTest::class.java.getResource("test.ics").toURI().toURL()
    val file = File(ParserTest::class.java.getResource("test.ics").toURI())

    @Test
    fun localFileTest() {
        val icsFile = FetchingIcsFile(fileURL)
        val reader = BufferedReader(FileReader(file))
        val testedReader = icsFile.content

        Assert.assertTrue(
                reader.lineSequence()
                .zip(testedReader.lineSequence())
                .all { (first, second) -> first == second }
        )

        reader.close()
        testedReader.close()
    }

    @Test
    fun internetFileTest() {
        val icsFile = FetchingIcsFile(URL("https://plan.polsl.pl/plan.php?type=0&id=652&cvsfile=true&wd=1"))
        icsFile.content.use {
            Assert.assertEquals("BEGIN:VCALENDAR", it.readLine())
        }
    }
}