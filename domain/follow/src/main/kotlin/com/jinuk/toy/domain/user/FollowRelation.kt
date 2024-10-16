package com.jinuk.toy.domain.user

data class FollowRelation(
    val followerUserId: Long,
    val followingUserId: Long
) {
    init {
        require(followerUserId != followingUserId) { "팔로워와 팔로잉 대상은 같을 수 없습니다." }
    }
}
