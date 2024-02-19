package com.board.apigateway.common.exception

import org.springframework.http.HttpStatus

/**
 * @author gihyung.lee
 * @since 2024-02-19
 */
enum class GatewayErrorMessage(
        val errorCode: String,
        val httpStatus: HttpStatus
){
    //

}