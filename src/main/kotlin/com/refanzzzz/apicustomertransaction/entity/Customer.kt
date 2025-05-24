package com.refanzzzz.apicustomertransaction.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "m_customers")
@EntityListeners(AuditingEntityListener::class)
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate", nullable = false)
    var birthdate: LocalDate? = null,

    @Column(name = "birthplace", nullable = false)
    var birthplace: String? = null,

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User? = null
): Auditable()