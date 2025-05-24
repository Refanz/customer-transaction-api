package com.refanzzzz.apicustomertransaction.dto.request

data class CustomerUpdateAccountRequest(
    val username: String,
    val email: String,
    val password: String,
)