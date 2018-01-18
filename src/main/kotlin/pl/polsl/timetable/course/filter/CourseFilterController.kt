package pl.polsl.timetable.course.filter

import io.swagger.annotations.*
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import pl.polsl.timetable.course.category.IdentifiableCategory
import pl.polsl.timetable.user.CustomUserDetails
import pl.polsl.timetable.user.User
import pl.polsl.timetable.user.UserService
import java.security.Principal

@RestController
class CourseFilterController(
        private val courseFilterService: CourseFilterService
) {
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.PUT, RequestMethod.POST], value= ["/filters"])
    fun addFilter(
            @AuthenticationPrincipal
            userDetails: CustomUserDetails,

            @ApiParam(value = "Allowed values for \"week\": \"EveryWeek\" or \"EveryTwoWeeks\"")
            @RequestBody
            filterData: CourseFilterDefinition
    ) {
        courseFilterService.createFilter(userDetails.user, filterData)
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.DELETE], value= ["/filter/{filterId}"])
    fun deleteFilter(@AuthenticationPrincipal userDetails: CustomUserDetails, @PathVariable filterId: Long) {
        courseFilterService.deleteFilter(userDetails.user, filterId)
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.GET], value= ["/filters"])
    fun filters(@AuthenticationPrincipal userDetails: CustomUserDetails): List<IdentifiableCourseFilterData> {
        return courseFilterService.filters(userDetails.user)
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/week_intervals"])
    fun weekIntervals() = WeekInterval.translations()
}