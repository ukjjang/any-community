package com.anycommunity.domain.point.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.anycommunity.domain.point.jpa.PointTransactionRepository
import com.anycommunity.util.custompage.toCustomPage

@Service
class PointTransactionQueryService(
    private val pointTransactionRepository: PointTransactionRepository,
) {
    fun findByUserIdOrderByIdDesc(userId: Long, pageable: Pageable) =
        pointTransactionRepository.findByUserIdOrderByIdDesc(userId, pageable).toCustomPage()
}
