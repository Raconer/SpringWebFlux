package com.spring.webflux.config

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun v1Api(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("v1")
            .pathsToMatch("/v1/**")
            .build()
    }

    @Bean
    fun v2Api(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("v2")
            .pathsToMatch("/v2/**")
            .build()
    }
}