package com.spring.webflux.content.controller.v1

import com.spring.webflux.common.DebugWebFluxTestSupport
import com.spring.webflux.common.dto.CommonRes
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContentControllerTest : DebugWebFluxTestSupport() {
    @Autowired
    override lateinit var webTestClient: WebTestClient

    @Test
    fun getContentsTest() {
        val result = webTestClient
            .get().uri("/v1/content")
            .accept(MediaType.APPLICATION_JSON).exchange()
            .expectStatus().isOk.returnResult(CommonRes::class.java)

        val bodyList = result.responseBody.collectList().block()
        setResponseBody(bodyList)
    }


}