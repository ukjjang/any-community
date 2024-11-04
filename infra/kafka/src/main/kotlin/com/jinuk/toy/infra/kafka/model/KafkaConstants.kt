package com.jinuk.toy.infra.kafka.model

object KafkaTopic {
    const val DEAD_LETTER = "dead-letter"

    object Comment {
        const val CREATE = "toy-comment-create"
        const val DELETE = "toy-comment-delete"
    }

    object Like {
        const val ADD = "toy-like-add"
        const val CANCEL = "toy-like-cancel"
    }
}

object KafkaGroupId {
    object Post {
        const val INCREASE_COMMENT_COUNT = "toy-post-increaseCommentCount"
        const val DECREASE_COMMENT_COUNT = "toy-post-decreaseCommentCount"
    }

    object Like {
        const val INCREASE_LIKE_COUNT = "toy-like-increaseCount"
        const val DECREASE_LIKE_COUNT = "toy-like-decreaseCount"
    }
}
