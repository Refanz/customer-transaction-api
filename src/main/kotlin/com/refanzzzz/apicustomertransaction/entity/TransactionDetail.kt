package com.refanzzzz.apicustomertransaction.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "t_transaction_details")
data class TransactionDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,
    val transactionId: String,
    val productId: String,
    val quantity: Int,
    val price: Double
): Auditable()
