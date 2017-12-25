package pl.polsl.timetable.user

import org.hamcrest.Matchers
import org.hamcrest.Matchers.hasValue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.web.context.WebApplicationContext
import javax.annotation.Resource
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcConfigurer


@RunWith(SpringRunner::class)
@SpringBootTest
class EndpointTests {
    @Resource
    lateinit var webApplicationContext: WebApplicationContext

    private lateinit var mock: MockMvc

    @Before
    fun setUp() {
        mock = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply<DefaultMockMvcBuilder>(springSecurity())
                .build()
    }

    @Test
    fun `accessing "users me" endpoint should return unauthorized status for unauthorized user`() {
        mock

                .perform(get("/user/me").header("origin",""))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun `registering and logging in should succeed`() {
        mock
                .perform(
                        post("/users")
                                .header("origin","")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content("""{"email":"test@email.com","password":"admin1"}""")
                )
                .andExpect(status().is2xxSuccessful)

        val session = mock
                .perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content("""{"email":"test@email.com","password":"admin1"}""")
                                .header("origin","")
                )
                .andExpect(status().is2xxSuccessful)
                .andReturn()
                .request
                .session

        Assert.assertNotNull(session)
        Assert.assertNotNull(session as? MockHttpSession )

        mock
            .perform(
                    get("/user/me")
                            .header("origin","")
                            .session(session as MockHttpSession)
            )
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.email").value("test@email.com"))
            .andExpect(jsonPath("$.passwordHash").doesNotExist())
    }

    @Test
    fun `posting user with invalid email should return 4xx status`() {
        mock
                .perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content("""{"email":"invalid_email","password":"admin1"}""")
                                .header("origin","")
                )
                .andExpect(status().is4xxClientError)
    }

}