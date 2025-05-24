package com.refanzzzz.apicustomertransaction.dto.response

data class TransactionResponse(
    val id: String,
    val customerName: String,
    val paymentMethod: String,
    val paymentStatus: String,
    val createdAt: String,
    val updatedAt: String,
    val transactionDetails: MutableList<TransactionDetailResponse>,
)
