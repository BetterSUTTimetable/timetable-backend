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
    fun basicResourceAccessWithRegistrationScenario() {
        mock
                .perform(get("/users/me").header("Origin", "cos"))
                .andExpect(status().isUnauthorized)

        mock
                .perform(post("/users").header("Origin", "cos").contentType(MediaType.APPLICATION_JSON_UTF8).content("""{"email":"test@email.com","password":"admin1"}"""))
                .andExpect(status().is2xxSuccessful)

        val session = mock
                .perform(post("/login").header("Origin", "cos").contentType(MediaType.APPLICATION_JSON_UTF8).content("""{"email":"test@email.com","password":"admin1"}"""))
                .andExpect(status().is2xxSuccessful)
                .andReturn()
                .request
                .session

        Assert.assertNotNull(session)
        Assert.assertNotNull(session as? MockHttpSession )

        val text = mock
                .perform(get("/users/me").header("Origin", "cos").session(session as MockHttpSession))
                .andExpect(status().is2xxSuccessful)
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.passwordHash").doesNotExist())
    }

    @Test
    fun emailValidation() {
        mock
                .perform(post("/users").header("Origin", "cos").contentType(MediaType.APPLICATION_JSON_UTF8).content("""{"email":"invalid_email","password":"admin1"}"""))
                .andExpect(status().is4xxClientError)
    }

}