package com.jinuk.toy.common.define.point

import com.fasterxml.jackson.annotation.JsonCreator

@JvmInline
value class Point private constructor(
    val value: Long,
) {
    companion object {
        @JsonCreator
        fun from(point: Long) = Point(point)

        operator fun invoke(point: Long) = from(point)
    }
}
