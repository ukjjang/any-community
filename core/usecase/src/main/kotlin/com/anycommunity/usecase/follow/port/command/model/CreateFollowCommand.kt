package com.anycommunity.usecase.follow.port.command.model

import com.anycommunity.domain.follow.FollowRelation

data class CreateFollowCommand(
    val followRelation: FollowRelation,
)
