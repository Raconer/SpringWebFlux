package com.spring.webflux.content.service

import com.spring.webflux.common.domain.Content
import reactor.core.publisher.Flux

interface ContentService {
    fun getList(): Flux<Content>
}