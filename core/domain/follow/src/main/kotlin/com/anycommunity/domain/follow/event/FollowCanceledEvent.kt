package com.anycommunity.domain.follow.event

import com.anycommunity.domain.follow.FollowRelation

data class FollowCanceledEvent(
    val followRelation: FollowRelation,
)
