package com.jinuk.toy.common.define.global.kafka

object KafkaGroupId {
    object User {
        const val INCREASE_FOLLOW_COUNT = "toy-user-increaseFollowCount"
        const val DECREASE_FOLLOW_COUNT = "toy-user-decreaseFollowCount"
    }

    object Post {
        const val INCREASE_COMMENT_COUNT = "toy-post-increaseCommentCount"
        const val DECREASE_COMMENT_COUNT = "toy-post-decreaseCommentCount"
    }

    object Like {
        const val INCREASE_LIKE_COUNT = "toy-like-increaseCount"
        const val DECREASE_LIKE_COUNT = "toy-like-decreaseCount"
    }
}