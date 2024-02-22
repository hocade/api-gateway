package com.board.apigateway.common.utils

/**
 * Collection 유틸리티 클래스
 * @author gihyung.lee
 * @since 2024-02-22
 */
class CollectionUtil {
    fun <T> Collection<T>.any(predicate: (T) -> Boolean): Boolean {
        for (element in this) {
            if (predicate(element)) {
                return true
            }
        }
        return false
    }
}