package pl.polsl.timetable.synchronization.scraper

import java.io.BufferedReader
import java.net.URL
import java.nio.channels.Channels


class IcsFileDownloader: (String) -> BufferedReader {
    override fun invoke(url: String): BufferedReader {
        val channel = Channels.newChannel(URL(url).openStream())
        val reader = Channels.newReader(channel, "UTF-8")
        return reader.buffered()
    }
}