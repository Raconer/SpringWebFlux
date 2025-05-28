package com.spring.webflux.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.Duration
import java.time.Instant

@TestPropertySource(properties = ["jasypt.encryptor.password=temp"])
abstract class DebugWebFluxTestSupport {
    protected open lateinit var webTestClient: WebTestClient

    protected val mapper = ObjectMapper().registerKotlinModule()

    private lateinit var startTime: Instant
    protected lateinit var lastRequestInfo: String
    protected lateinit var lastResponseInfo: String
    protected var lastResponseBodyJson: String? = null

    @BeforeEach
    fun setupLoggingFilter() {
        webTestClient = webTestClient.mutate()
            .responseTimeout(Duration.ofSeconds(30))
            .filter { request, next ->
                // 요청 로그 수집
                lastRequestInfo = buildString {
                    appendLine("🔸[REQUEST] ${request.method()} ${request.url()}")
                    request.headers().forEach { (k, v) ->
                        appendLine("  ➜ $k: ${v.joinToString()}")
                    }
                }

                startTime = Instant.now()

                next.exchange(request).doOnNext { response ->
                    val duration = Duration.between(startTime, Instant.now()).toMillis()
                    lastResponseInfo = buildString {
                        appendLine("🔹[RESPONSE] Status: ${response.statusCode()}, Time: ${duration}ms")
                        response.headers().asHttpHeaders().forEach { (k, v) ->
                            appendLine("  ⇦ $k: ${v.joinToString()}")
                        }
                    }
                }
            }
            .build()
    }

    @AfterEach
    fun printDebugSummary() {
        println("\n✅ 테스트 종료 후 전체 요청/응답 정보 요약:")
        println(lastRequestInfo)
        println(lastResponseInfo)
        println("📦 응답 바디 (JSON Pretty):\n${lastResponseBodyJson ?: "(바디 없음)" }")
    }

    // 제거 할지 고민
    protected fun setResponseBody(body: Any?) {
        lastResponseBodyJson = mapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(body)
    }
}