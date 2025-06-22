package com.mumulbo.notification.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.mumulbo.notification.dto.NotificationEventDto
import com.mumulbo.notification.service.NotificationService
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NotificationKafkaConsumer(
    private val objectMapper: ObjectMapper,
    private val notificationService: NotificationService
) {
    private val logger = LoggerFactory.getLogger(NotificationKafkaConsumer::class.java)

    @KafkaListener(topics = ["notification-events"], groupId = "notification-service")
    fun consume(message: String) {
        try {
            val event = objectMapper.readValue(message, NotificationEventDto::class.java)
            logger.info("✅ Kafka 메시지 수신: {}", message)
            notificationService.handle(event)
        } catch (e: Exception) {
            logger.error("❌ Kafka 메시지 처리 실패: {}", message, e)
        }
    }
}
