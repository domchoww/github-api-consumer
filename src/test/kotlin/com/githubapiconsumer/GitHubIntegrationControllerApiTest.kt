package com.githubapiconsumer

import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_XML


class GitHubIntegrationControllerApiTest : IntegrationTest() {

    @Test
    fun `should call github repository service`() {
        // given
        mockServer.enqueueJsonResponse(200, repositoryEndpointResponse)
        mockServer.enqueueJsonResponse(200, branchesEndpointResponse)
        val owner = "someOwner"
        val expectedResponse =
            """[{"name":"SpringApplication","owner":"someOwner","branches":[{"name":"master","sha":"1234"}]}]"""

        // when
        webTestClient.get().uri(String.format(USER_REPOSITORY_PATH, owner))
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .json(expectedResponse)
    }

    @Test
    fun `should return expected response code when unsupported accept header received`() {
        // given
        val acceptHeader = APPLICATION_XML

        // when
        webTestClient.get().uri(String.format(USER_REPOSITORY_PATH, "foo"))
            .accept(acceptHeader)
            .exchange()
            .expectStatus()
            .isEqualTo(406)
            .expectBody()
            .jsonPath("$.Message")
            .isEqualTo("Requested media type is not supported")
    }

    @Test
    fun `should return expected response code reposiotry owner not found`() {
        // given
        mockServer.enqueueJsonResponse(404, "{}")
        val owner = "someOwner"

        // when
        webTestClient.get().uri(String.format(USER_REPOSITORY_PATH, owner))
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectBody()
            .jsonPath("$.Message")
            .isEqualTo("Resource not found: $owner")
    }

    companion object {
        private const val USER_REPOSITORY_PATH = "/user/%s/repositories"
    }
}