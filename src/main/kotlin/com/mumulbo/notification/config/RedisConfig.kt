package com.mumulbo.notification.config

import com.mumulbo.notification.subscriber.RedisSubscriber
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.listener.PatternTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer

/**
 * Redis 구독 리스너 컨테이너 설정
 * RedisSubscriber를 Redis pub/sub 채널에 연결하여 메시지 수신을 처리함
 */
@Configuration
class RedisConfig {

    @Bean
    fun redisMessageListenerContainer(
        connectionFactory: RedisConnectionFactory,
        redisSubscriber: RedisSubscriber
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        container.addMessageListener(redisSubscriber, PatternTopic("notification:*"))
        return container
    }
}
