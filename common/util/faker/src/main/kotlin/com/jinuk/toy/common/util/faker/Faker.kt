package com.jinuk.toy.common.util.faker

import net.datafaker.Faker

val faker: Faker = Faker()

fun Faker.randomLong() = this.random().nextLong()
fun Faker.randomLongPositive() = this.random().nextLong(1, Long.MAX_VALUE)

fun Faker.randomString(length: Int = 40): String = this.lorem().characters(length)

inline fun <reified T : Enum<T>> Faker.randomEnum(): T = this.options().option(T::class.java)
