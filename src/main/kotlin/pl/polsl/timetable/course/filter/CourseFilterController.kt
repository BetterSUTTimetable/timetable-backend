package pl.polsl.timetable.course.filter

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import pl.polsl.timetable.course.category.IdentifiableCategory
import pl.polsl.timetable.user.UserService
import java.security.Principal

@RestController
class CourseFilterController(
        private val userService: UserService,
        private val courseFilterService: CourseFilterService
){

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.PUT, RequestMethod.POST], value= ["/filters"])
    fun addFilter(principal: Principal?, @RequestBody filterData: CourseFilterDefinition) {
        val id = userService.find(principal!!).id
        courseFilterService.createFilterForUser(id, filterData)
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.GET], value= ["/filters"])
    fun filters(principal: Principal?): List<IdentifiableCourseFilterData> {
        val id = userService.find(principal!!).id
        TODO()
    }
}