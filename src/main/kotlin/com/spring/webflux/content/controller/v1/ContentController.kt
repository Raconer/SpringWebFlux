package com.spring.webflux.content.controller.v1

import com.spring.webflux.common.domain.Content
import com.spring.webflux.content.service.ContentService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux


@Tag(
    name = "[BackOffice]강연 조회 API",
    description = """
        강연 정보를 조회하거나 신청자 목록을 확인하는 백오피스용 API입니다.
    """
)
@RestController
@RequestMapping("/v1/content")
class ContentController (
    private val contentService: ContentService
){

    @GetMapping
    fun getContents(): Flux<Content> {
        return this.contentService.getList()
    }
}