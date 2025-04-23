package kr.co.green.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 클라이언트가 WebSocket 연결할 때 사용할 엔드포인트 등록
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 엔드포인트: ws://localhost:8080/ws
                .setAllowedOriginPatterns("*") // CORS 허용
                .withSockJS(); // 브라우저 호환성
    }

    // 메시지 라우팅 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // 메시지 브로커 주소 (구독)
        config.setApplicationDestinationPrefixes("/app"); // 메시지 보낼 때 prefix
    }
}
