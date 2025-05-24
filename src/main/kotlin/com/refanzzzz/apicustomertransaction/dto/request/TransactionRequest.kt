package com.refanzzzz.apicustomertransaction.dto.request

data class TransactionRequest(
    val customerId: String,
    val paymentId: String,
)
