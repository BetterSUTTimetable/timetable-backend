package pl.polsl.timetable.user

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController(private val userService: UserService) {
    @RequestMapping(method = arrayOf(RequestMethod.POST, RequestMethod.PUT), value="/users")
    @ApiOperation(
            value = "Register a new user."
    )
    fun register(@RequestBody user: UserLoginData) {
        userService.create(user)
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = arrayOf(RequestMethod.GET), value="/users/me")
    fun currentUser(principal: Principal?): User{
        return userService.find(principal!!)
    }
}