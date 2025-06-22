package com.mumulbo.notification.repository

import com.mumulbo.notification.model.Notification
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : MongoRepository<Notification, String> {
    fun findByUserIdOrderByCreatedAtDesc(userId: Long): List<Notification>
    fun findByUserIdAndIsReadFalse(userId: Long): List<Notification>
    fun countByUserIdAndIsReadFalse(userId: Long): Long
    fun existsByEventId(eventId: String): Boolean
}
