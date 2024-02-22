package com.board.apigateway.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.server.SecurityWebFilterChain


/**
 * Spring security confifg
 * @author gihyung.lee
 * @since 2024-02-22
 */
@Configuration
class SecurityConfig {

    @Bean
    @Order(1)
    fun oauth2ClientSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
                .authorizeExchange { exchanges ->
                    exchanges
                            .pathMatchers("/login/oauth2/code/**").permitAll() // 인증 없이 접근 허용하는 경로
                            .anyExchange().authenticated() // 나머지 부분은 인증처리
                }
                .oauth2Login(withDefaults())

        return http.build()
    }

    @Bean
    @Order(2)
    fun resourceServerSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        http
                .authorizeExchange { exchanges ->
                    exchanges
                            .anyExchange().authenticated() // 나머지 부분은 인증처리
                }
                .oauth2ResourceServer { resourceServer ->
                    resourceServer
                            .jwt() // OAuth 2.0 JWT 토큰을 사용하여 인증
                }

        return http.build()
    }
}