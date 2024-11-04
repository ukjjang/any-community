package com.jinuk.toy.consumer.global

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import com.jinuk.toy.infra.kafka.model.KafkaMessage

@Component
class KafkaEventParser(private val objectMapper: ObjectMapper) {
    fun <T> parse(
        message: String,
        payloadClass: Class<T>,
    ): T {
        return parse<T>(message, objectMapper.constructType(payloadClass)).payload
    }

    private fun <T> parse(
        message: String,
        payloadType: JavaType,
    ): KafkaMessage<T> {
        require(message.isNotBlank()) { "Message is blank" }

        try {
            return objectMapper.readValue(message, convertToMessageType(payloadType))
        } catch (e: Exception) {
            throw IllegalArgumentException("could not parse the message: $message", e)
        }
    }

    private fun convertToMessageType(payloadType: JavaType): JavaType {
        return objectMapper.typeFactory.constructParametricType(KafkaMessage::class.java, payloadType)
    }
}
