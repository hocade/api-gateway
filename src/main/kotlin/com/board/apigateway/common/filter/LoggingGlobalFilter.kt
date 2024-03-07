package com.board.apigateway.common.filter;

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * @author gihyung.lee
 * @since 2024-03-07
 */
@Slf4j
@Component
class LoggingGlobalFilter : GlobalFilter, Ordered {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        logger.info("RequestId : ${exchange.request.id}, RequestMethod: ${exchange.request.method}, RequestUri: ${exchange.request.uri}")

        return chain.filter(exchange).then(Mono.fromRunnable {
            if(exchange.response.statusCode?.is2xxSuccessful == true) {
                logger.info("ResponseCode: ${exchange.response.statusCode}")
            } else {
                logger.error("ResponseCode: ${exchange.response.statusCode}")
            }
        })
    }

    override fun getOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE
    }
}
