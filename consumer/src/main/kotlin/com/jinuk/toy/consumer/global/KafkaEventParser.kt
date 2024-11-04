package com.jinuk.toy.consumer.global

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import org.springframework.stereotype.Component
import com.jinuk.toy.util.logger.LazyLogger

@Component
class KafkaEventParser(private val objectMapper: ObjectMapper) {
    companion object {
        internal val log by LazyLogger()
    }

    fun <T> parse(
        message: String,
        clazz: Class<T>,
    ): T {
        return try {
            objectMapper.readValue(message, TypeFactory.defaultInstance().constructType(clazz))
        } catch (e: RuntimeException) {
            log.error { "consume error = ${e.message}" }
            throw e
        }
    }
}
