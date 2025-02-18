package com.anycommunity.domain.user

import java.time.LocalDateTime
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.definition.point.Point
import com.anycommunity.definition.user.Gender
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.shared.BaseDomain
import com.anycommunity.util.jbcrypt.Jbcrypt

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

    internal fun updateFollowingCount(countOperation: CountOperation) = this.copy(
        followingCount = followingCount + countOperation.delta,
    )

    internal fun updateFollowerCount(countOperation: CountOperation) = this.copy(
        followerCount = followerCount + countOperation.delta,
    )

    internal fun updateTotalPoints(point: Point) = this.copy(
        totalPoints = (totalPoints + point)
            .also { require(it >= Point.ZERO) { "포인트가 부족합니다." } },
    )

    companion object {
        internal fun signup(userCredentials: UserCredentials, gender: Gender) = User(
            username = userCredentials.username,
            password = Jbcrypt.encrypt(userCredentials.password),
            gender = gender,
        )
    }
}
