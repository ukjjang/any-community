package com.jinuk.toy.infra.redis.lock

import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext

object CustomSpELParser {
    fun dynamicValue(
        parameterNames: Array<String>,
        args: Array<Any>,
        key: String,
    ): Any? {
        val context = StandardEvaluationContext()
        for (i in parameterNames.indices) {
            context.setVariable(parameterNames[i], args[i])
        }
        return SpelExpressionParser()
            .parseExpression(key)
            .getValue(context, Any::class.java)
    }
}
