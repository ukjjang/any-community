package com.jinuk.toy.domain.follow.event

import com.jinuk.toy.domain.follow.FollowRelation

data class FollowCanceledEvent(
    val followRelation: FollowRelation,
)
