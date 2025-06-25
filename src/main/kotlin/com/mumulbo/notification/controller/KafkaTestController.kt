package com.mumulbo.notification.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mumulbo.notification.dto.NotificationEventDto
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/notification/test-notify")
class KafkaTestController(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    @PostMapping
    fun sendTestNotification(@RequestBody dto: NotificationEventDto): ResponseEntity<Void> {
        val json = objectMapper.writeValueAsString(dto)
        kafkaTemplate.send("notification-events", json)
        return ResponseEntity.ok().build()
    }
}