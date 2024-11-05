package com.jinuk.toy.domain.user

import java.time.LocalDateTime
import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.infra.rdb.user.entity.UserEntity
import com.jinuk.toy.util.domainhelper.BaseDomain
import com.jinuk.toy.util.jbcrypt.Jbcrypt

data class User(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val username: Username,
    val password: String,
    val followingCount: Long = 0L,
    val followerCount: Long = 0L,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (_id != other._id) return false

        return true
    }

    override fun hashCode(): Int {
        return _id?.hashCode() ?: 0
    }

    companion object {
        fun signup(userCredentials: UserCredentials) =
            User(username = userCredentials.username, password = Jbcrypt.encrypt(userCredentials.password))
    }
}

internal fun UserEntity.toModel() =
    User(
        _id = id,
        username = Username(username),
        password = password,
        followingCount = followingCount,
        followerCount = followerCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

internal fun User.toEntity() =
    UserEntity(
        id = _id,
        username = username.value,
        password = password,
        followingCount = followingCount,
        followerCount = followerCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
