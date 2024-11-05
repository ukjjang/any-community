package com.jinuk.toy.domain.like

import org.springframework.stereotype.Component
import com.jinuk.toy.constant.like.LikeType
import com.jinuk.toy.domain.like.jpa.LikeRepository
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomEnum
import com.jinuk.toy.util.faker.randomLong
import com.jinuk.toy.util.faker.randomString

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
