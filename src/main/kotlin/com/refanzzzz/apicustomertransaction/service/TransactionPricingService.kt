package com.refanzzzz.apicustomertransaction.service

interface TransactionPricingService {
    fun calculateProductSubtotal(productPrice: Long, quantity: Int): Long
    fun calculateProductTotalTaxAmount(
        productSubtotalAmount: Long,
        taxRate: Double
    ): Long
    fun calculateProductGrandTotalAmount(
        productSubtotalAmount: Long,
        productTotalTaxAmount: Long
    ): Long
}