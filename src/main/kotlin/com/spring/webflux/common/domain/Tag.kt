package com.spring.webflux.common.domain

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("tag", schema = "study")
data class Tag(
    @Id
    @Schema(description = "태그 ID", example = "100")
    val id: Long? = null,
    @Schema(description = "태그 이름", example = "spring")
    val name: String,
):CommonEntity()