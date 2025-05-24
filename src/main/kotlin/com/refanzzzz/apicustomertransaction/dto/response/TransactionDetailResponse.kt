package com.refanzzzz.apicustomertransaction.dto.response

data class TransactionDetailResponse(
    var id: String? = null,
    var productName: String? = null,
    var quantity: Int? = null,
    var priceAtTransaction: Long = 0,
    var productGrandTotalAmount: Long = 0,
    var productSubtotalAmount: Long = 0,
    var productTotalTaxAmount: Long = 0,
)