package com.board.apigateway.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

/**
 * 외부 서비스랑 통신하기 위한 설정
 * @author gihyung.lee
 * @since 2024-02-22
 */
@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder().build();
    }

}