package com.mumulbo.notification.subscriber

import com.fasterxml.jackson.databind.ObjectMapper
import com.mumulbo.notification.util.WebSocketSessionManager
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage

@Service
class RedisSubscriber(
    private val sessionManager: WebSocketSessionManager,
    private val objectMapper: ObjectMapper
) : MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val topic = String(message.channel)
        val msgText = String(message.body)
        println("🔵 RedisSubscriber 수신 → topic=$topic, message=$msgText")

        val userId = topic.removePrefix("notification:").toLongOrNull() ?: return
        val session = sessionManager.get(userId)
        if (session == null) {
            println("❗ WebSocket 세션 없음: userId=$userId")
            return
        }

        session.sendMessage(TextMessage(msgText))
        println("✅ WebSocket 전송 완료: userId=$userId")
    }
}
