package pl.polsl.timetable.course.category

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryController(
        @Autowired
        private val categoryService: CategoryService
) {
    @RequestMapping(method = arrayOf(RequestMethod.GET), value="/categories")
    fun rootCategories(): Set<IdentifiableCategory> {
        return categoryService.rootCategories()
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value="/category/{id}/subcategories")
    fun subcategories(@PathVariable id: Long): Set<IdentifiableCategory> {
        return categoryService.subcategoriesOf(id)
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value="/category/{id}")
    fun category(@PathVariable id: Long): ChildrenAwareCategory {
        return categoryService.category(id)
    }
}

