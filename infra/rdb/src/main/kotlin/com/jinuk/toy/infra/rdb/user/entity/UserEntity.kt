package com.jinuk.toy.infra.rdb.user.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import com.jinuk.toy.common.define.point.Point
import com.jinuk.toy.common.define.user.Gender
import com.jinuk.toy.common.define.user.Username

@Entity
@Table(name = "user")
class UserEntity(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "username")
    val username: Username,
    @Column(name = "password")
    val password: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    val gender: Gender,
    @Column(name = "total_points")
    val totalPoints: Point,
    @Column(name = "following_count")
    val followingCount: Long,
    @Column(name = "follower_count")
    val followerCount: Long,
    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
