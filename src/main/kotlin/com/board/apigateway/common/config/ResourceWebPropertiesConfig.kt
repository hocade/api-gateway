package com.board.apigateway.common.config

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * @author gihyung.lee
 * @since 2024-03-07
 */
@Configuration
class ResourceWebPropertiesConfig {
    @Bean
    fun resources(): WebProperties.Resources {
        return WebProperties.Resources()
    }
}