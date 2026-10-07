package es.unizar.webeng.lab2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.TestRestTemplate
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class ErrorPageTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var client: TestRestTemplate

    @Test
    fun unknownPathRendersErrorHtml() {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.TEXT_HTML)
        val response = client.exchange(
            "http://127.0.0.1:$port/missing",
            HttpMethod.GET,
            HttpEntity<Void>(headers),
            String::class.java,
        )

        assertNotNull(response, "Response error: Response should not be null")

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)

        val body = response.body

        assertNotNull(body, "Body error: Response body should not be null")

        assertTrue(body.isNotEmpty(), "Content error: Response body should not be empty")

        val expectedText = "Custom error page"
        assertTrue(body.contains(expectedText)) {
            """
            Validation error: Expected text not found in response body.
            -> Expected to find: "$expectedText"
            -> Actual response body is (${body.length} characters):
            ------------------------------------------------
            $body
            ------------------------------------------------
            """.trimIndent()

        }
    }
}