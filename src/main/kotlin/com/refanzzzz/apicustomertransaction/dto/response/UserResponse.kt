package com.refanzzzz.apicustomertransaction.dto.response

data class UserResponse(
    val id: String,
    val username: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String,
    val role: List<String>,
)