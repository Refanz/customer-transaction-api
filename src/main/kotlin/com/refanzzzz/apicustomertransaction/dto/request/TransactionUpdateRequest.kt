package com.refanzzzz.apicustomertransaction.dto.request

data class TransactionUpdateRequest(
    var paymentId: String? = null,
    var paymentStatus: String? = null
)