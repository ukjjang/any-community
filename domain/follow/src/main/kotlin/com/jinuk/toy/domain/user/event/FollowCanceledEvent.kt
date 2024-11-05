package com.jinuk.toy.domain.user.event

import com.jinuk.toy.domain.user.FollowRelation

data class FollowCanceledEvent(
    val followRelation: FollowRelation,
)
