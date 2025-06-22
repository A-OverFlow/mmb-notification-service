package com.mumulbo.notification.service

import com.mumulbo.notification.dto.NotificationEventDto
import com.mumulbo.notification.model.Notification
import com.mumulbo.notification.repository.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val redisPublisher: RedisPublisher
) {
    /**
     * Kafka에서 받은 이벤트 처리
     * 1. 중복 체크 (eventId)
     * 2. MongoDB 저장
     * 3. Redis Pub → WebSocket 브로드캐스트
     */
    fun handle(event: NotificationEventDto) {
        // 🔐 중복 이벤트 수신 방지
        if (notificationRepository.existsByEventId(event.eventId)) return

        val notification = Notification(
            eventId = event.eventId,
            userId = event.receiverUserId,
            type = event.notificationType,
            message = event.message,
            relatedUrl = event.relatedUrl,
            metadata = event.metadata
        )

        // DB 저장
        notificationRepository.save(notification)

        // Redis Pub → WebSocket 브로드캐스트
        redisPublisher.publish(event) // ✅ 수정 포인트
    }

    /**
     * 사용자 알림 목록 조회
     */
    fun getUserNotifications(userId: Long) =
        notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)

    /**
     * 안 읽은 알림 개수 조회
     */
    fun getUnreadCount(userId: Long) =
        notificationRepository.countByUserIdAndIsReadFalse(userId)

    /**
     * 특정 알림 읽음 처리
     */
    fun markAsRead(userId: Long, id: String) {
        val notification = notificationRepository.findById(id)
        if (notification.isPresent && notification.get().userId == userId) {
            val entity = notification.get()
            entity.isRead = true
            notificationRepository.save(entity)
        }
    }

    /**
     * 모든 안 읽은 알림 읽음 처리
     */
    fun markAllAsRead(userId: Long) {
        val unread = notificationRepository.findByUserIdAndIsReadFalse(userId)
        unread.forEach { it.isRead = true }
        notificationRepository.saveAll(unread)
    }
}
