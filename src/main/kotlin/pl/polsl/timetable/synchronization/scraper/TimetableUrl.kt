package pl.polsl.timetable.synchronization.scraper

import java.net.URL

fun generateTimetablesUrls(basicUrl: URL): List<URL> {
    val parameters = listOf(
            "&wd=1&winW=1584&winH=818&loadBG=000000", //winter semester
            "&wd=4&winW=1584&winH=818&loadBG=000000"  //summer semester
    )

    return parameters
            .map { basicUrl.toString() + it }
            .map { URL(it) }
}