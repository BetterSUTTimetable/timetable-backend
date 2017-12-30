package pl.polsl.timetable.course.filter

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import pl.polsl.timetable.course.category.IdentifiableCategory
import pl.polsl.timetable.user.User
import pl.polsl.timetable.user.UserService
import java.security.Principal

@RestController
class CourseFilterController(
        private val courseFilterService: CourseFilterService
) {
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.PUT, RequestMethod.POST], value= ["/filters"])
    fun addFilter(@AuthenticationPrincipal user: User, @RequestBody filterData: CourseFilterDefinition) {
        courseFilterService.createFilter(user, filterData)
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.DELETE], value= ["/filter/{id}"])
    fun deleteFilter(@AuthenticationPrincipal user: User, @PathVariable filterId: Long) {
        courseFilterService.deleteFilter(user, filterId)
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.GET], value= ["/filters"])
    fun filters(@AuthenticationPrincipal user: User): List<IdentifiableCourseFilterData> {
        return user.filters.flatMap { it.value }
    }
}