package uk.gov.justice.digital.hmpps.digitalprisonreportinglib.integration

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.http.HttpHeader
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
abstract class IntegrationTestBase {

  @Value("\${spring.security.user.roles}")
  lateinit var authorisedRole: String

  @Autowired
  lateinit var webTestClient: WebTestClient

  @Autowired
  lateinit var jwtAuthHelper: JwtAuthHelper

  companion object {

    private lateinit var wireMockServer: WireMockServer

    @BeforeAll
    @JvmStatic
    fun setupClass() {
      wireMockServer = WireMockServer(
        WireMockConfiguration.wireMockConfig().port(9999),
      )
      wireMockServer.start()
    }

    @AfterAll
    @JvmStatic
    fun teardownClass() {
      wireMockServer.stop()
    }
  }

  @BeforeEach
  fun setup() {
//    stubGrantToken()
    stubMeCaseloadsResponse(createCaseloadJsonResponse("WWI"))
  }

  fun stubGrantToken() {
    wireMockServer.stubFor(
      WireMock.post(WireMock.urlEqualTo("/auth/oauth/token"))
        .willReturn(
          WireMock.aResponse()
            .withHeaders(com.github.tomakehurst.wiremock.http.HttpHeaders(HttpHeader("Content-Type", "application/json")))
            .withBody(
              """{
                    "token_type": "bearer",
                    "access_token": "ABCDE"
                }
              """.trimIndent(),
            ),
        ),
    )
  }
  protected fun stubMeCaseloadsResponse(jsonNode: JsonNode) {
    wireMockServer.stubFor(
      WireMock.get("/me/caseloads").willReturn(
        WireMock.aResponse()
          .withStatus(HttpStatus.OK.value())
          .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .withJsonBody(createCaseloadJsonResponse("WWI")),
      ),
    )
  }

  protected fun createCaseloadJsonResponse(activeCaseloadId: String): JsonNode = ObjectMapper().readTree(
    """
          {
            "username": "TESTUSER1",
            "active": true,
            "accountType": "GENERAL",
            "activeCaseload": {
              "id": "$activeCaseloadId",
              "name": "WANDSWORTH (HMP)"
            },
            "caseloads": [
              {
                "id": "WWI",
                "name": "WANDSWORTH (HMP)"
              }
            ]
          }
    """.trimIndent(),
  )

  internal fun setAuthorisation(
    user: String = "AUTH_ADM",
    roles: List<String> = listOf(),
    scopes: List<String> = listOf(),
  ): (HttpHeaders) -> Unit = jwtAuthHelper.setAuthorisation(user, roles, scopes)
}
