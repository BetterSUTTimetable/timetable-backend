package pl.polsl.timetable.synchronization.scraper

import java.io.BufferedReader

interface IcsFile {
    val content: BufferedReader
}