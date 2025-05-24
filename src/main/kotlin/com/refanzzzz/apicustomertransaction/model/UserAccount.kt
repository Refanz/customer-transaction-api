package com.refanzzzz.apicustomertransaction.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "m_user_accounts")
data class UserAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String
)
