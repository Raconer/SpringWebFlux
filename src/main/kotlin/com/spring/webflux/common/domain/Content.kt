package com.spring.webflux.common.domain

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("content", schema = "study")
@Schema(name = "Lecture", description = "강연 데이터")
data class Content(
    @Id
    @Schema(description = "콘텐츠 ID", example = "10")
    val id: Long? = null,
    @Schema(description = "작성자 ID", example = "1")
    val userId: Long,
    @Schema(description = "콘텐츠 제목", example = "Spring WebFlux란?")
    val title: String,
    @Schema(description = "콘텐츠 본문", example = "WebFlux는 비동기 논블로킹 웹 프레임워크입니다.")
    val body: String?,
):CommonEntity()
