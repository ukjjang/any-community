package com.jinuk.toy.domain.user

import java.time.LocalDateTime
import com.jinuk.toy.common.util.domainhelper.BaseDomain
import com.jinuk.toy.common.util.jbcrypt.Jbcrypt
import com.jinuk.toy.common.value.global.CountOperation
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.common.value.user.Gender
import com.jinuk.toy.common.value.user.Username
import com.jinuk.toy.infra.rdb.user.entity.UserEntity

@ConsistentCopyVisibility
data class User internal constructor(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val username: Username,
    val password: String,
    val gender: Gender,
    val totalPoints: Point = Point(0),
    val followingCount: Long = 0L,
    val followerCount: Long = 0L,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        if (_id != other._id) return false
        return true
    }

    override fun hashCode() = _id?.hashCode() ?: 0

    fun updateFollowingCount(countOperation: CountOperation) = this.copy(
        followingCount = followingCount + countOperation.delta,
    )

    fun updateFollowerCount(countOperation: CountOperation) = this.copy(
        followerCount = followerCount + countOperation.delta,
    )

    fun updateTotalPoints(point: Point) = this.copy(
        totalPoints = (totalPoints + point)
            .also { require(it >= Point.ZERO) { "포인트가 부족합니다." } },
    )

    companion object {
        fun signup(userCredentials: UserCredentials, gender: Gender) = User(
            username = userCredentials.username,
            password = Jbcrypt.encrypt(userCredentials.password),
            gender = gender,
        )
    }
}

internal fun UserEntity.toModel() = User(
    _id = id,
    username = username,
    password = password,
    gender = gender,
    totalPoints = totalPoints,
    followingCount = followingCount,
    followerCount = followerCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

internal fun User.toEntity() = UserEntity(
    id = _id,
    username = username,
    password = password,
    gender = gender,
    totalPoints = totalPoints,
    followingCount = followingCount,
    followerCount = followerCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
