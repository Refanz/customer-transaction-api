package com.refanzzzz.apicustomertransaction.util

object JWTUtil {
    fun parseJWTToken(bearerToken: String): String {
        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }

        return ""
    }
}