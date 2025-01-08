package com.anycommunity.infra.mysql.user.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import com.anycommunity.definition.user.Username
import com.anycommunity.infra.mysql.user.entity.UserEntity

interface UserEntityRepository : JpaRepository<UserEntity, Long> {
    fun existsByUsername(username: Username): Boolean

    fun findByUsername(username: Username): UserEntity?

    fun findByOrderByTotalPointsDesc(pageable: Pageable): Page<UserEntity>
}
