package com.jinuk.toy.domain.user.mapper

import com.jinuk.toy.domain.user.User
import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.infra.rdb.user.entity.UserEntity

internal object UserEntityMapper {
    fun UserEntity.toModel() = User(
        id = id,
        username = Username(username),
        password = password,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    fun User.toEntity() = UserEntity(
        id = id,
        username = username.value,
        password = password,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
