package com.mumulbo.notification.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@EnableKafka
@Configuration
class KafkaConsumerConfig {

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val props = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "mmb-kafka:9092", // <-- application.yml과 통일
            ConsumerConfig.GROUP_ID_CONFIG to "notification-service",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,

            // 필수 안정성 옵션
            ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG to "60000",          // default: 10초 → 60초
            ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG to "20000",       // default: 3초 → 20초
            ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to "300000",       // default: 5분
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to "500",              // 메시지 많이 처리할 경우
        )
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(props)
        return factory
    }
}