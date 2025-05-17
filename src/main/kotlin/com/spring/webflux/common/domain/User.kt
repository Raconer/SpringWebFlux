package com.spring.webflux.common.domain

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(value = "`user`", schema = "study")
@Schema(name = "사용자", description = "서비스 사용자")
data class User(
    @Id
    @Schema(description = "사용자 ID", example = "1")
    val id: Long? = null,
    @Schema(description = "사용자 이름", example = "홍길동")
    val name: String,
    @Schema(description = "사용자 이메일", example = "hong@example.com")
    val email: String,
) : CommonEntity()