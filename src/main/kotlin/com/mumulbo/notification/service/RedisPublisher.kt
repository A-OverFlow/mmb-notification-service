package com.mumulbo.notification.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mumulbo.notification.dto.NotificationEventDto
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisPublisher(
    private val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper
) {
    fun publish(event: NotificationEventDto) {
        val channel = "notification:${event.receiverUserId}"
        val message = objectMapper.writeValueAsString(event)
        redisTemplate.convertAndSend(channel, message)
        println("📨 Redis publish → channel=$channel, message=$message")
    }
}
