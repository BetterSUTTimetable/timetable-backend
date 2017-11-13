package pl.polsl.timetable.course

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest
class CourseControllerTest {
    @Resource
    lateinit var webApplicationContext: WebApplicationContext

    private lateinit var mock: MockMvc

    @Before
    fun setUp() {
        mock = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    @Test
    fun displayingCourse() {
        mock
                .perform(MockMvcRequestBuilders.get("/courses/0?from=2018-01-23T07:30:00.00Z&to=2018-01-23T10:30:00.00Z"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }
}