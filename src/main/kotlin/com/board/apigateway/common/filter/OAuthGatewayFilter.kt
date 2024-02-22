package com.board.apigateway.common.filter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * api-route의 Oauth2.0 추가 작업을 진행하는 필터
 * @author gihyung.lee
 * @since 2024-02-22
 */
@Component
class OAuthGatewayFilter @Autowired constructor(
        private val webClient: WebClient
) : GatewayFilter {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {

        return ReactiveSecurityContextHolder.getContext()
                .flatMap { context: SecurityContext ->
                    val authentication = context.authentication
                    if (authentication is JwtAuthenticationToken) {
                        // OAuth 2.0 토큰이 있는 경우
                        // 토큰의 유효성 검증 및 사용자의 인증 상태 및 리소스 접근 권한 확인

                        // 토큰 검증 및 사용자의 인증 상태 확인
                        if (authentication.isAuthenticated) {
                            // 사용자의 리소스 접근 권한 확인
                            val authorities = authentication.authorities

                            if (authorities.stream().anyMatch { it.authority == "ROLE_USER" }) {
                                return@flatMap chain.filter(exchange)
                            }
                        }
                    }

                    // 토큰이 유효하지 않거나 권한이 없는 경우 401 Unauthorized 응답 반환
                    exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                    exchange.response.setComplete()
                    Mono.empty()
                }
    }

}