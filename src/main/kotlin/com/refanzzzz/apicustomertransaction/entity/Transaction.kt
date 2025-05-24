package com.refanzzzz.apicustomertransaction.entity

import com.refanzzzz.apicustomertransaction.constant.PaymentStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import java.time.LocalDateTime

@Entity
@Table(name = "t_transactions")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_date", nullable = false)
    var transactionDate: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    var paymentStatus: PaymentStatus? = null,

    @Column(name = "net_amount_paid", nullable = true)
    var netAmountPaid: Long? = null,

    @Column(name = "total_amount_paid", nullable = true)
    var totalAmountPaid: Long? = null,

    @Column(name = "total_tax_paid", nullable = true)
    var totalTaxPaid: Long? = null,

    @ManyToOne
    @JoinColumn(name = "payment_id")
    var payment: Payment? = null,

    @OneToMany(mappedBy = "transaction", cascade = [CascadeType.ALL])
    var transactionDetails: MutableList<TransactionDetail>? = null,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null

) : Auditable() {

    @PrePersist
    fun prePersist() {
        transactionDate = LocalDateTime.now()
    }
}
