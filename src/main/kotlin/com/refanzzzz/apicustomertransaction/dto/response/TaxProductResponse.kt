package com.refanzzzz.apicustomertransaction.dto.response

data class TaxProductResponse(
    val id: String,
    val taxName: String,
    val taxAmount: Long,
    val taxRate: Double,
)
