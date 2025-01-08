package com.anycommunity.usecase.post.usecase.command

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.definition.point.PointRuleType
import com.anycommunity.domain.point.service.PointRuleQueryService
import com.anycommunity.domain.post.Post
import com.anycommunity.domain.post.event.PostCreatedEvent
import com.anycommunity.domain.post.service.PostCommandService
import com.anycommunity.domain.shared.outbox.OutboxCreator
import com.anycommunity.infra.redis.lock.timeBoundLock
import com.anycommunity.usecase.point.port.command.model.PointProcessCommand
import com.anycommunity.usecase.point.usecase.command.internal.PointProcessUsecase
import com.anycommunity.usecase.post.port.command.model.CreatePostCommand
import com.anycommunity.usecase.post.port.command.model.CreatePostResult

@Service
class CreatePostUsecase(
    private val postCommandService: PostCommandService,
    private val pointRuleQueryService: PointRuleQueryService,
    private val pointProcessUsecase: PointProcessUsecase,
    private val outboxCreator: OutboxCreator,
) {
    companion object {
        private const val POINT_DESCRIPTION_TEMPLATE = "게시글 작성으로 포인트 지급 | 게시글 ID: "
    }

    @Transactional
    operator fun invoke(command: CreatePostCommand): CreatePostResult {
        val post = postCommandService.create(command.toInfo())
            .also {
                outboxCreator.create(KafkaTopic.Post.CREATE, PostCreatedEvent.from(it))

                timeBoundLock(
                    key = "CreatePostUsecase:pointProcess:${command.userId}",
                    leaseTime = 10,
                    timeUnit = TimeUnit.MINUTES,
                ) {
                    pointProcess(it)
                }
            }
        return CreatePostResult.from(post)
    }

    private fun pointProcess(post: Post) {
        val pointRule = pointRuleQueryService.getByRuleType(PointRuleType.POST_CREATION)
        val processCommand = PointProcessCommand(
            userId = post.userId,
            point = pointRule.amount,
            description = "$POINT_DESCRIPTION_TEMPLATE${post.id}",
        )
        pointProcessUsecase(processCommand)
    }
}
