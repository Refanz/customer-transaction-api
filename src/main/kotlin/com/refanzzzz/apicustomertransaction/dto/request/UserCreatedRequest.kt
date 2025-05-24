package com.refanzzzz.apicustomertransaction.dto.request

data class UserCreatedRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: List<String>
)
