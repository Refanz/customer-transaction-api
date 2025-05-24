package com.refanzzzz.apicustomertransaction.entity

import jakarta.persistence.*

@Entity
@Table(name = "t_transaction_details")
data class TransactionDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    @Column(name = "product_quantity", nullable = false)
    var quantity: Int? = 1,

    @Column(name = "price_at_transaction", nullable = false)
    var priceAtTransaction: Long? = 0,

    @Column(name = "product_subtotal_amount", nullable = false)
    var productSubtotalAmount: Long? = 0,

    @Column(name = "product_total_tax_amount", nullable = false)
    var productTotalTaxAmount: Long? = 0,

    @Column(name = "product_grand_total_amount", nullable = false)
    var productGrandTotalAmount: Long? = 0,

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null,

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    var transaction: Transaction? = null,
) : Auditable()
