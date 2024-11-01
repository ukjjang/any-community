package com.jinuk.toy.infra.kafka

import com.jinuk.toy.infra.kafka.model.KafkaTopic
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.KafkaUtils
import org.springframework.util.backoff.FixedBackOff
import java.time.LocalDateTime

@Configuration
class KafkaConfig(
    private val kafkaProperty: KafkaProperty,
) {
    companion object {
        const val LISTENER_FACTORY = "concurrentKafkaStringListenerContainerFactory"
    }

    @Bean
    fun kafkaStringProducerFactory(): DefaultKafkaProducerFactory<String, String> {
        val props =
            mapOf(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperty.bootstrapServers,
                CommonClientConfigs.SECURITY_PROTOCOL_CONFIG to kafkaProperty.securityProtocol,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.LINGER_MS_CONFIG to 10,
                ProducerConfig.MAX_BLOCK_MS_CONFIG to 20000,
            )

        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    fun kafkaStringTemplate(): KafkaTemplate<String, String> {
        return KafkaTemplate(kafkaStringProducerFactory())
    }

    @Bean
    fun kafkaStringConsumerConfig(): Map<String, Any> {
        return mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperty.bootstrapServers,
            CommonClientConfigs.SECURITY_PROTOCOL_CONFIG to kafkaProperty.securityProtocol,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to kafkaProperty.offsetReset,
        )
    }

    @Bean
    fun kafkaStringConsumerFactory(): DefaultKafkaConsumerFactory<String, String> {
        return DefaultKafkaConsumerFactory(kafkaStringConsumerConfig())
    }

    @Bean
    @DependsOn("kafkaStringTemplate", "kafkaStringConsumerFactory")
    fun concurrentKafkaStringListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = kafkaStringConsumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.RECORD
        factory.setCommonErrorHandler(DefaultErrorHandler(deadLetterRecoverer(), FixedBackOff(10L, 0)))
        return factory
    }

    private fun deadLetterRecoverer(): DeadLetterPublishingRecoverer {
        return DeadLetterPublishingRecoverer(kafkaStringTemplate()) { record, exception ->
            record.headers().add("originTopic", record.topic().toByteArray())
            record.headers().add("originPartition", record.partition().toString().toByteArray())
            record.headers().add("originOffset", record.offset().toString().toByteArray())
            record.headers().add("consumerGroupId", KafkaUtils.getConsumerGroupId().toByteArray())
            record.headers().add("deadLetterAt", LocalDateTime.now().toString().toByteArray())
            record.headers().add("errorMessage", exception.stackTraceToString().toByteArray(Charsets.UTF_8))
            TopicPartition(KafkaTopic.DEAD_LETTER.topicName, -1)
        }
    }
}
