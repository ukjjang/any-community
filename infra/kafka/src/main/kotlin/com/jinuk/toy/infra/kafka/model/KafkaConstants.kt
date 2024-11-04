package com.jinuk.toy.infra.kafka.model

object KafkaTopic {
    const val DEAD_LETTER = "dead-letter"

    object Comment {
        const val CREATE = "toy-comment-create"
    }
}

object KafkaGroupId {
    object Post {
        const val INCREASE_COMMENT_COUNT = "toy-post-increaseCommentCount"
    }
}
