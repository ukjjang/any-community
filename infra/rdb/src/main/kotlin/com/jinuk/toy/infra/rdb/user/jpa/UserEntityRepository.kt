package com.jinuk.toy.infra.rdb.user.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.jinuk.toy.common.value.user.Username
import com.jinuk.toy.infra.rdb.user.entity.UserEntity

interface UserEntityRepository : JpaRepository<UserEntity, Long> {
    fun existsByUsername(username: Username): Boolean

    fun findByUsername(username: Username): UserEntity?
}
