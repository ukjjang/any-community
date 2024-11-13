package com.jinuk.toy.domain.like

import com.jinuk.toy.common.value.like.LikeType

data class LikeTarget(
    val type: LikeType,
    val id: String,
) {
    companion object {
        fun from(
            type: LikeType,
            id: String,
        ) = LikeTarget(type, id)

        fun from(
            type: LikeType,
            id: Long,
        ) = from(type, id.toString())
    }

    init {
        validate(type, id)
    }

    private fun validate(
        type: LikeType,
        id: String,
    ) {
        when (type) {
            LikeType.POST,
            LikeType.COMMENT,
            -> require(isNumeric(id)) { "$type ID는 숫자형이어야 합니다." }
        }
    }

    private fun isNumeric(id: String): Boolean {
        return id.toLongOrNull() != null
    }
}
