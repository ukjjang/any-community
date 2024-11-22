package com.anycommunity.definition.global.kafka

object KafkaGroupId {
    object User {
        const val INCREASE_FOLLOW_COUNT = "anycommunity-user-increaseFollowCount"
        const val DECREASE_FOLLOW_COUNT = "anycommunity-user-decreaseFollowCount"
    }

    object Post {
        const val INCREASE_COMMENT_COUNT = "anycommunity-post-increaseCommentCount"
        const val DECREASE_COMMENT_COUNT = "anycommunity-post-decreaseCommentCount"
    }

    object Like {
        const val INCREASE_LIKE_COUNT = "anycommunity-like-increaseCount"
        const val DECREASE_LIKE_COUNT = "anycommunity-like-decreaseCount"
    }
}
