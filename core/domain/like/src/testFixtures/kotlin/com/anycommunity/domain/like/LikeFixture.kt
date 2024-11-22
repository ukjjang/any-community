package com.anycommunity.domain.like

import org.springframework.stereotype.Component
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.like.jpa.LikeRepository
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomEnum
import com.anycommunity.util.faker.randomLong
import com.anycommunity.util.faker.randomString

@Component
class LikeFixture(
    private val likeRepository: LikeRepository,
) {
    companion object {
        fun create(
            userId: Long = faker.randomLong(),
            targetType: LikeType = faker.randomEnum<LikeType>(),
            targetId: String = faker.randomString(),
        ) = Like(
            userId = userId,
            targetType = targetType,
            targetId = targetId,
        )
    }

    fun persist(
        userId: Long = faker.randomLong(),
        targetType: LikeType = faker.randomEnum<LikeType>(),
        targetId: String = faker.randomString(),
    ) = likeRepository.save(create(userId, targetType, targetId))
}
