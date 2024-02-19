package com.board.apigateway.common.exception

/**
 * @author gihyung.lee
 * @since 2024-02-19
 */
class GatewayException(val errorMsg: GatewayErrorMessage) : RuntimeException() {
}