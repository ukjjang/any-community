package com.anycommunity.usecase.follow.port.query.model

import java.time.LocalDateTime
import com.anycommunity.definition.user.Username

data class GetFollowingResult(
    val id: Long,
    val username: Username,
    val createdAt: LocalDateTime,
)