package com.jinuk.toy.common.define.global.kafka

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

    object Follow {
        const val ADD = "toy-follow-add"
        const val CANCEL = "toy-follow-cancel"
    }
}
