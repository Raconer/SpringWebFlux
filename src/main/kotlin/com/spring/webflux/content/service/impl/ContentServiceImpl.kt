package com.spring.webflux.content.service.impl

import com.spring.webflux.common.domain.Content
import com.spring.webflux.content.service.ContentService
import com.spring.webflux.repository.ContentRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ContentServiceImpl(
    private val contentRepository: ContentRepository
) : ContentService {


    override fun getList(): Flux<Content> {
        return this.contentRepository.findAll()
    }
}