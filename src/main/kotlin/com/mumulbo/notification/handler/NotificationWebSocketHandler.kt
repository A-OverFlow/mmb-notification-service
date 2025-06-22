package com.mumulbo.notification.handler

import com.mumulbo.notification.util.WebSocketSessionManager
import org.springframework.stereotype.Component
import org.springframework.web.socket.*
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class NotificationWebSocketHandler(
    private val sessionManager: WebSocketSessionManager
) : TextWebSocketHandler() {

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId = session.uri?.query?.split("=")?.getOrNull(1)?.toLongOrNull()
        if (userId != null) {
            sessionManager.register(userId, session)
            println("✅ WebSocket 연결 성공: sessionId=${session.id}, userId=$userId")
        } else {
            println("❌ userId 없음. 연결 종료")
            session.close()
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionManager.removeBySession(session)
    }

    private fun WebSocketSessionManager.removeBySession(session: WebSocketSession) {
        //val userId = sessions.entries.find { it.value.id == session.id }?.key
        //if (userId != null) remove(userId)
    }
}
