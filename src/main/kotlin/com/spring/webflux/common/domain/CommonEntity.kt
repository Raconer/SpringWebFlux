package com.spring.webflux.common.domain

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

abstract class CommonEntity {
    @Column("created_at")
    @Schema(description = "생성일시", example = "2024-01-01T12:00:00")
    var createdAt: LocalDateTime = LocalDateTime.now()
    @Column("updated_at")
    @Schema(description = "수정일시", example = "2024-01-02T15:30:00")
    var updatedAt: LocalDateTime = LocalDateTime.now()
}