package com.anycommunity.definition.global.kafka

object KafkaTopic {
    const val DEAD_LETTER = "dead-letter"

    object Post {
        const val DELETE = "anycommunity-post-delete"
        const val CREATE = "anycommunity-post-create"
    }

    object Follow {
        const val UNFOLLOW = "anycommunity-follow-unFollow"
    }
}
