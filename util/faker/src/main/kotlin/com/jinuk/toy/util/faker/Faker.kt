package com.jinuk.toy.util.faker

import net.datafaker.Faker

val faker: Faker = Faker()

fun Faker.randomLong() = this.random().nextLong()
fun Faker.randomString(length: Int = 40): String = this.lorem().characters(length)

