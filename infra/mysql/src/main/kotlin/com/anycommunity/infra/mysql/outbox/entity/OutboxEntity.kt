package com.anycommunity.infra.mysql.outbox.entity

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
import com.anycommunity.definition.outbox.OutboxStatus

@Entity
@Table(name = "outbox")
class OutboxEntity(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "topic")
    val topic: String,
    @Column(name = "payload")
    val payload: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: OutboxStatus = OutboxStatus.INIT,
    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
