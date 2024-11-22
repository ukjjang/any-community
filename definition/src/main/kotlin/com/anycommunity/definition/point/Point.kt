package com.anycommunity.definition.point

@JvmInline
value class Point(
    val value: Long,
) : Comparable<Point> {
    operator fun plus(point: Point) = Point(value + point.value)
    override fun compareTo(other: Point): Int = this.value.compareTo(other.value)

    companion object {
        val ZERO = Point(0L)
    }
}
