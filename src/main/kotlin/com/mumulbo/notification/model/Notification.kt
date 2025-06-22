package com.mumulbo.notification.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "notification")
data class Notification(
    @Id
    val id: String? = null,
    val eventId: String,
    val userId: Long,
    val type: NotificationType,
    val message: String,
    val relatedUrl: String?,
    val metadata: Map<String, Any>? = null,
    var isRead: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
