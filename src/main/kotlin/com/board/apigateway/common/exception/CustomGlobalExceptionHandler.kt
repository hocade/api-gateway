package com.board.apigateway.common.exception

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono

/**
 * exception 공통 처리
 * @author gihyung.lee
 * @since 2024-02-19
 */
@Component
class CustomGlobalExceptionHandler(
        errorAttributes: ErrorAttributes,
        resources: WebProperties.Resources,
        applicationContext: ApplicationContext,
        serverCodecConfigurer: ServerCodecConfigurer,
): AbstractErrorWebExceptionHandler(
        errorAttributes, resources, applicationContext
) {

    init {
        super.setMessageReaders(serverCodecConfigurer.readers)
        super.setMessageWriters(serverCodecConfigurer.writers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> =
            RouterFunctions.route(RequestPredicates.all(), this::handleError)

    fun handleError(request: ServerRequest): Mono<ServerResponse> {
        return when (val throwable = super.getError(request)) {
            is GatewayException -> {
                val errorMsg = throwable.errorMsg

                ServerResponse.status(errorMsg.httpStatus)
                        .bodyValue(
                                BaseExceptionResponse(
                                        errorCode = errorMsg.errorCode,
                                        message = "에러 코드로 메시지 파싱해서 전달해야함",
                                )
                        )
            }
            else -> {
                ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue(
                                BaseExceptionResponse(
                                        errorCode = "",
                                        message = "throwable.message",
                                )
                        )
            }
        }
    }

}