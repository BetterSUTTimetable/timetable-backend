package pl.polsl.timetable.user

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import pl.polsl.timetable.course.Course
import pl.polsl.timetable.course.CourseService
import pl.polsl.timetable.course.JpaCourse
import pl.polsl.timetable.course.category.IdentifiableCategory
import java.security.Principal
import java.time.Instant

@RestController
class UserController(
        private val userService: UserService,
        private val coursesService: CourseService
) {
    @RequestMapping(method = [RequestMethod.POST, RequestMethod.PUT], value = ["/users"])
    @ApiOperation(
            value = "Register a new user."
    )
    fun register(@RequestBody user: UserLoginData) {
        userService.create(user)
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.GET], value = ["/user/me"])
    fun currentUser(authentication: Authentication): User {
        return authentication.user()
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.GET], value = ["/courses"])
    fun currentUserCourses(
            authentication: Authentication,

            @ApiParam(
                    value = "Date and time in ISO 8601 format acceptable " +
                            "by `java.time.Instant.parse` e.g. `2017-11-15T08:00:00Z`",
                    required = true
            )
            @RequestParam(name = "from", required = true)
            from: Instant,

            @ApiParam(
                    value = "Date and time in ISO 8601 format acceptable " +
                            "by `java.time.Instant.parse` e.g. `2017-11-15T10:30:00Z`",
                    required = true
            )
            @RequestParam(name = "to", required = true)
            to: Instant
    ): List<Course> {
        return coursesService.userCoursesBetween(authentication.user(), from..to)
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.GET], value = ["/selected_categories"])
    fun selectedCategories(@AuthenticationPrincipal user: User): Set<IdentifiableCategory> {
        return user.selectedCategories
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.GET], value = ["/selected_category/{categoryId}"])
    fun selectedCategory(authentication: Authentication, @PathVariable categoryId: Long): IdentifiableCategory {
        TODO()
        //return currentUser.selectedCategories.first { it.id == categoryId }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.POST], value = ["/selected_categories"])
    fun addSelectedCategory(principal: Principal?, @RequestBody categoryId: Long) {
        userService.addSelectedCategory(principal!!, categoryId)
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.DELETE], value = ["/selected_category/{categoryId}"])
    fun removeSelectedCategory(principal: Principal?, @PathVariable categoryId: Long) {
        userService.removeSelectedCategory(principal!!, categoryId)
    }
}