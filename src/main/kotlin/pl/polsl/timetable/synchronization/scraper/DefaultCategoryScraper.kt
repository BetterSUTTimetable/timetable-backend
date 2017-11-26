package pl.polsl.timetable.synchronization.scraper

import com.github.michaelbull.result.*
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

    override fun scrape(): Result<Category, Throwable> {
        val categoryId = Regex("branch=(\\d*)")
                .find(url.toString())
                ?.groups
                ?.get(1)
                ?.value
                ?.toLongOrNull()

        return if (categoryId != null) {
            createInnerCategory(categoryId)
        } else {
            Result.of {
                RootCategory(Jsoup.connect(url.toString()).get(), this::createInnerCategory)
            }
        }
    }

    private fun createInnerCategory(id: Long): Result<Category, Throwable> {
        logger.info("Scraping category: $url")

        val category = Result.of {
            val url = "https://plan.polsl.pl/left_menu_feed.php?type=1&branch=$id&link=0"
            val document = Jsoup.connect(url).get()
            InnerCategory(document, this::createInnerCategory, this::createLeafCategory)
        }

        category.onFailure { logger.error("Cannot create inner category $url", it) }

        return category
    }

    private fun createLeafCategory(leafUrl: URL): Result<Category, Throwable> {
        //TODO: change this sick logic please...
        logger.info("Scraping timetable: $leafUrl")

        val category = Result.of {
            URL(leafUrl.toString() + "&winW=1584&winH=818&loadBG=000000")
        }
                .andThen { timetablePageFactory.create(it) }
                .map { LeafCategory(it, coursesBuilder) }


        category.onFailure { logger.warn("Cannot create leaf category $url . $it") }

        return category
    }
}