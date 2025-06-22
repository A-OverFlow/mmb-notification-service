package com.mumulbo.notification.dto

import com.mumulbo.notification.model.NotificationType

data class NotificationEventDto(
    val eventId: String, // 고유 이벤트 ID
    val receiverUserId: Long,
    val notificationType: NotificationType,
    val message: String,
    val relatedUrl: String? = null,
    val metadata: Map<String, Any>? = null

)
