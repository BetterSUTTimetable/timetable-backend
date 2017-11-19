package pl.polsl.timetable.synchronization.scraper

import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import pl.polsl.timetable.synchronization.CoursesBuilder
import java.net.URL

@Component
class DefaultCategoryScraper(
        @Value("\${timetable.category-url}")
        url: String,

        @Autowired
        private val coursesBuilder: CoursesBuilder,

        @Autowired
        private val timetablePageFactory: TimetablePageFactory
): CategoryScraper {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val url = URL(url)

    override fun scrape(): Category {
        val categoryId = Regex("branch=(\\d*)")
                .find(url.toString())
                ?.groups
                ?.get(1)
                ?.value
                ?.toLongOrNull()

        if (categoryId != null) {
            return createInnerCategory(categoryId)
        } else {
            val document = Jsoup.connect(url.toString()).get()
            return RootCategory(document, this::createInnerCategory)
        }
    }

    private fun createInnerCategory(id: Long): Category {
        //TODO: remove this logic from here?
        val url = "https://plan.polsl.pl/left_menu_feed.php?type=1&branch=$id&link=0"
        logger.info("Scraping category: $url")
        val document = Jsoup.connect(url).get()
        return InnerCategory(document, this::createInnerCategory, this::createLeafCategory)
    }

    private fun createLeafCategory(leafUrl: URL): Category {
        //TODO: change this sick logic please...
        val timetableUrl = URL(leafUrl.toString() + "&winW=1584&winH=818&loadBG=000000")
        logger.info("Scraping timetable: $timetableUrl")
        return LeafCategory(timetablePageFactory.create(timetableUrl), coursesBuilder)
    }
}