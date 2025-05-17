package com.spring.webflux.common.domain

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("content_tag", schema = "study")
data class ContentTag(
    @Column("content_id")
    @Schema(description = "콘텐츠 ID", example = "10")
    val contentId: Long,
    @Schema(description = "태그 ID", example = "100")
    val tagId: Long
) : CommonEntity()
