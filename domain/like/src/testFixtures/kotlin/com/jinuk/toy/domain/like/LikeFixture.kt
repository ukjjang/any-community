package com.jinuk.toy.domain.like

import org.springframework.stereotype.Component
import com.jinuk.toy.common.define.like.LikeType
import com.jinuk.toy.common.util.faker.faker
import com.jinuk.toy.common.util.faker.randomEnum
import com.jinuk.toy.common.util.faker.randomLong
import com.jinuk.toy.common.util.faker.randomString
import com.jinuk.toy.domain.like.jpa.LikeRepository

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
