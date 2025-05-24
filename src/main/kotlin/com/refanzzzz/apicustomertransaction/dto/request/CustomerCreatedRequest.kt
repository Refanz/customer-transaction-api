package com.refanzzzz.apicustomertransaction.dto.request

data class CustomerCreatedRequest(
    val username: String,
    val email: String,
    val password: String,
    val name: String,
    val birthdate: String,
    val birthplace: String,
)
