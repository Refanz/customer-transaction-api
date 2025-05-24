package com.refanzzzz.apicustomertransaction.dto.response

data class LoginResponse(
    val role: String,
    val messages: String,
    val accessToken: String
)
