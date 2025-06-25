package com.mumulbo.notification.controller

import com.mumulbo.notification.dto.ApiResponse
import com.mumulbo.notification.service.NotificationService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/notification")
class NotificationController(
    private val notificationService: NotificationService
) {

    @GetMapping
    fun getNotifications(request: HttpServletRequest): ResponseEntity<ApiResponse<Any>> {
        val userId = request.getHeader("X-User-Id")?.toLongOrNull() ?: return ResponseEntity.badRequest().build()
        val data = notificationService.getUserNotifications(userId)
        return ResponseEntity.ok(ApiResponse(data))
    }

    @GetMapping("/unread-count")
    fun getUnreadCount(request: HttpServletRequest): ResponseEntity<ApiResponse<Map<String, Long>>> {
        val userId = request.getHeader("X-User-Id")?.toLongOrNull() ?: return ResponseEntity.badRequest().build()
        val count = notificationService.getUnreadCount(userId)
        return ResponseEntity.ok(ApiResponse(mapOf("unreadCount" to count)))
    }

    @PatchMapping("/{id}/read")
    fun markAsRead(@PathVariable id: String, request: HttpServletRequest): ResponseEntity<ApiResponse<Unit>> {
        val userId = request.getHeader("X-User-Id")?.toLongOrNull() ?: return ResponseEntity.badRequest().build()
        notificationService.markAsRead(userId, id)
        return ResponseEntity.ok(ApiResponse(Unit))
    }

    @PatchMapping("/read-all")
    fun markAllAsRead(request: HttpServletRequest): ResponseEntity<ApiResponse<Unit>> {
        val userId = request.getHeader("X-User-Id")?.toLongOrNull() ?: return ResponseEntity.badRequest().build()
        notificationService.markAllAsRead(userId)
        return ResponseEntity.ok(ApiResponse(Unit))
    }
}
