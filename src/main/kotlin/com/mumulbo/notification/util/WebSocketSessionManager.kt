package com.mumulbo.notification.util

import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class WebSocketSessionManager {
    private val sessions = ConcurrentHashMap<Long, WebSocketSession>()

    fun register(userId: Long, session: WebSocketSession) {
        sessions[userId] = session
    }

    fun remove(userId: Long) {
        sessions.remove(userId)
    }

    fun get(userId: Long): WebSocketSession? = sessions[userId]
}