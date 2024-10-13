package com.jinuk.toy.infra.rdb.user.jpa

import com.jinuk.toy.infra.rdb.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserEntityRepository : JpaRepository<UserEntity, Long> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): UserEntity?
}
