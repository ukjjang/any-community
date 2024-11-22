package com.anycommunity.definition.global.kafka

object KafkaTopic {
    const val DEAD_LETTER = "dead-letter"

    object Comment {
        const val CREATE = "anycommunity-comment-create"
        const val DELETE = "anycommunity-comment-delete"
    }

    object Like {
        const val ADD = "anycommunity-like-add"
        const val CANCEL = "anycommunity-like-cancel"
    }

    object Follow {
        const val ADD = "anycommunity-follow-add"
        const val CANCEL = "anycommunity-follow-cancel"
    }
}
