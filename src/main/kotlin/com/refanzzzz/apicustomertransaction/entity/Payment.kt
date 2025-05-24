package com.refanzzzz.apicustomertransaction.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "m_payments")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    @Column(name = "method", nullable = false)
    var method: String? = null,

    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "transaction_id")
    var transaction: Transaction? = null
) : Auditable()
