package com.anycommunity.usecase.user.port.command.model

import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.follow.FollowRelation

data class UpdateUserFollowCountCommand(
    val followRelation: FollowRelation,
    val countOperation: CountOperation,
)
