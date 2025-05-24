package com.refanzzzz.apicustomertransaction.dto.response

data class ProfileResponse(
    val id: String,
    val username: String,
    val name: String,
    val birthdate: String,
    val birthplace: String,
    val email: String,
    val role: List<String>
)
