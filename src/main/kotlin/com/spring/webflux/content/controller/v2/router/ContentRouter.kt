package com.spring.webflux.content.controller.v2.router

import com.spring.webflux.content.controller.v2.handler.ContentHandler
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration(proxyBeanMethods = false)
class ContentRouter {
    @Bean
    @RouterOperations(
        RouterOperation(
            path = "/v2/content",
            beanClass = ContentHandler::class,
            beanMethod = "getList",
            produces = [MediaType.APPLICATION_JSON_VALUE]
        )
    )
    fun getList(contentHandler: ContentHandler): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(
                RequestPredicates.GET("/v2/content").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                contentHandler::getList
            )
    }

}