package com.jinuk.toy.infra.rdb.post.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class PostEntity(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "title")
    val title: String,
    @Column(name = "content")
    val content: String,
    @Column(name = "comment_count")
    val commentCount: Long,
    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
