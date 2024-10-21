package com.jinuk.toy.infra.redis.lock

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class CustomSpELParserTest : DescribeSpec(
    {
        describe("CustomSpELParser") {
            it("주어진 SpeL 표현식에 대한 올바른 값 반환하는지 검증") {
                val parameterNames = arrayOf("arg1", "arg2")
                val args = arrayOf<Any>("Hello", "jinuk")
                val key = "#arg1 + ' world ' + #arg2"

                val result = CustomSpELParser.dynamicValue(parameterNames, args, key)

                result shouldBe "Hello world jinuk"
            }
        }
    },
)
