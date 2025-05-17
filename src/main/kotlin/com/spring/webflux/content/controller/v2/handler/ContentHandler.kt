package com.spring.webflux.content.controller.v2.handler

import com.spring.webflux.common.domain.Content
import com.spring.webflux.content.service.ContentService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ContentHandler(
    private val contentService: ContentService
) {
    fun getList(request: ServerRequest): Mono<ServerResponse> {
        return this.contentService.getList().collectList() .flatMap { contentList ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(contentList)
        }
            .switchIfEmpty(
                ServerResponse.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("ERROR")
            )
    }
}