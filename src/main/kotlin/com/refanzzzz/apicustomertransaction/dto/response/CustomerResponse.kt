package com.refanzzzz.apicustomertransaction.dto.response

data class CustomerResponse(
    val id: String,
    val name: String,
    val email: String,
    val username: String,
    val role: List<String>,
    val birthdate: String,
    val birthplace: String,
    val createdAt: String,
    val updatedAt: String
)
