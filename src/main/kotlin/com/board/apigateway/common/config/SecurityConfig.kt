package com.board.apigateway.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


/**
 * Spring security confifg
 * @author gihyung.lee
 * @since 2024-02-22
 */
@Configuration
class SecurityConfig {

    @Bean
    fun resourceServerSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        http
            .authorizeExchange { exchanges ->
                exchanges
                    .pathMatchers("/api/public/**").permitAll() // 인증 제외할 url
                    .anyExchange().authenticated() // 나머지 부분은 인증처리
            }
            .oauth2ResourceServer { resourceServer ->
                resourceServer
                    .jwt()
            }

        return http.build()
    }

}