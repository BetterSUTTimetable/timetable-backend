package pl.polsl.timetable.synchronization.parser

import org.junit.Assert
import org.junit.Test
import pl.polsl.timetable.synchronization.scraper.IcsFileDownloader
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.net.URL

class IcsFileDownloaderTest {
    val fileURL = IcsFileDownloaderTest::class.java.getResource("test.ics").toURI().toURL()
    val file = File(IcsFileDownloaderTest::class.java.getResource("test.ics").toURI())

    @Test
    fun localFileTest() {
        val icsFile = IcsFileDownloader()(fileURL.toString())
        val reader = BufferedReader(FileReader(file))
        val testedReader = icsFile

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
        val icsFile = IcsFileDownloader()("https://plan.polsl.pl/plan.php?type=0&id=652&cvsfile=true&wd=1")
        icsFile.use {
            Assert.assertEquals("BEGIN:VCALENDAR", it.readLine())
        }
    }
}