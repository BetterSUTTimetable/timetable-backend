package pl.polsl.timetable.synchronization

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pl.polsl.timetable.course.category.CategoryRepository
import pl.polsl.timetable.course.category.CategoryService
import pl.polsl.timetable.synchronization.scraper.DefaultCategory

@RunWith(SpringRunner::class)
@SpringBootTest
class CategoryBuildngServiceTest {
    @Autowired
    lateinit var categoryBuildingService: CategoryBuildingService

    @Autowired
    lateinit var categoryService: CategoryService

    @Test
    fun creatingSimpleCategoryHierarchy() {
        val root = DefaultCategory("root",
            listOf(
                    DefaultCategory(
                            "child 1",
                            listOf(
                                    DefaultCategory(
                                            "nested child 1",
                                            emptyList()
                                    ),
                                    DefaultCategory(
                                            "nested child 2",
                                            emptyList()
                                    )
                            )
                    ),
                    DefaultCategory(
                            "child 2",
                            listOf(
                                    DefaultCategory(
                                            "nested child 1",
                                            emptyList()
                                    ),
                                    DefaultCategory(
                                            "nested child 2",
                                            emptyList()
                                    )
                            )
                    )
            )
        )
        categoryBuildingService.recreate(root)
        val rootCategories = categoryService.rootCategories()

        Assert.assertEquals(2, rootCategories.size)
        Assert.assertNotNull(rootCategories.firstOrNull{it.name == "child 1"})
        Assert.assertNotNull(rootCategories.firstOrNull{it.name == "child 2"})

    }
}