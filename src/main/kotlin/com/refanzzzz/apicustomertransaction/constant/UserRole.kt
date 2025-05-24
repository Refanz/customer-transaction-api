package com.refanzzzz.apicustomertransaction.constant

enum class UserRole {
    USER("user"), ADMIN("admin");

    private val role: String

    constructor(role: String) {
        this.role = role
    }
}