package com.refanzzzz.apicustomertransaction.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "t_tax_details")
data class TaxDetail(
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    var id: String? = null,

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null,

    @ManyToOne
    @JoinColumn(name = "tax_id")
    var tax: Tax? = null,

    @Column(name = "tax_rate_at_sale", nullable = false)
    var taxRateAtSale: Double? = 0.0,

    @Column(name = "tax_amount", nullable = false)
    var taxAmount: Double? = 0.0,
) : Auditable()
