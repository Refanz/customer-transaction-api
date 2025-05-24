package com.refanzzzz.apicustomertransaction.constant

enum class UserRole {
    ROLE_USER("user"), ROLE_ADMIN("admin"), ROLE_CUSTOMER("customer");

    private val role: String

    constructor(role: String) {
        this.role = role
    }

    companion object {
        fun getRole(role: String): UserRole {
            return entries.firstOrNull { it.role.equals(role, ignoreCase = true) } ?: throw IllegalArgumentException()
        }
    }
}