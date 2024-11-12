package com.jinuk.toy.domain.follow.event

import com.jinuk.toy.domain.follow.FollowRelation

data class FollowAddedEvent(
    val followRelation: FollowRelation,
)
