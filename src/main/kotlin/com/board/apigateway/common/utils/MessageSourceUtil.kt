package com.board.apigateway.common.utils;

import com.board.apigateway.common.exception.GatewayErrorMessage
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.*

/**
 * @author gihyung.lee
 * @since 2024-03-07
 */
@Component
class MessageSourceUtil (
    private val messageSource: MessageSource
) {
    fun getMessage(code: String): String = messageSource.getMessage(
        code,
        arrayOf<String>(),
        Locale.getDefault()
    )

    fun getMessage(code: String, args: Array<String>) = messageSource.getMessage(
        code,
        args,
        Locale.getDefault()
    )

    fun getMessage(gatewayErrorMessage: GatewayErrorMessage) = getMessage(gatewayErrorMessage.errorCode)
}
