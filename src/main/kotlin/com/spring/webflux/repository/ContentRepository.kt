package com.spring.webflux.repository

import com.spring.webflux.common.domain.Content
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ContentRepository : ReactiveCrudRepository<Content, Long> {
}