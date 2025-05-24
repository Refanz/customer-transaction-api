package com.refanzzzz.apicustomertransaction.service.impl

import com.refanzzzz.apicustomertransaction.service.TransactionPricingService
import org.springframework.stereotype.Service

@Service
class TransactionPricingServiceImpl : TransactionPricingService {
    override fun calculateProductSubtotal(productPrice: Long, quantity: Int): Long {
        if (quantity <= 0) {
            throw IllegalArgumentException("Quantity must be greater than zero")
        }
        if (productPrice < 0) {
            throw IllegalArgumentException("Product price cannot be negative")
        }
        return productPrice * quantity
    }

    override fun calculateProductTotalTaxAmount(
        productSubtotalAmount: Long,
        taxRate: Double
    ): Long {
        if (taxRate < 0) {
            throw IllegalArgumentException("Tax rate cannot be negative")
        }
        if (productSubtotalAmount < 0) {
            throw IllegalArgumentException("Product subtotal amount cannot be negative")
        }
        return (productSubtotalAmount * taxRate).toLong()
    }

    override fun calculateProductGrandTotalAmount(
        productSubtotalAmount: Long,
        productTotalTaxAmount: Long
    ): Long {
        if (productSubtotalAmount < 0) {
            throw IllegalArgumentException("Product subtotal amount cannot be negative")
        }
        if (productTotalTaxAmount < 0) {
            throw IllegalArgumentException("Product total tax amount cannot be negative")
        }
        return productSubtotalAmount + productTotalTaxAmount
    }

}