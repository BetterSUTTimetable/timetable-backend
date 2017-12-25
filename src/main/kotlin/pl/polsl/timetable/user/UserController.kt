package pl.polsl.timetable.user

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.security.access.prepost.PreAuthorize
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
    @RequestMapping(method = [RequestMethod.POST, RequestMethod.PUT], value= ["/users"])
    @ApiOperation(
            value = "Register a new user."
    )
    fun register(@RequestBody user: UserLoginData) {
        userService.create(user)
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.GET], value= ["/user/me"])
    fun currentUser(principal: Principal?): User {
        return userService.find(principal!!)
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.GET], value= ["/user/me/courses"])
    fun currentUserCourses(
            principal: Principal?,

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
        val currentUser = userService.find(principal!!)
        return coursesService.userCoursesBetween(currentUser, from..to)
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = [RequestMethod.GET], value= ["/user/me/favorite_categories"])
    fun currentUserFavoriteCategories(principal: Principal?): Set<IdentifiableCategory> {
        val currentUser = userService.find(principal!!)
        return currentUser.favoriteCategories
    }
}