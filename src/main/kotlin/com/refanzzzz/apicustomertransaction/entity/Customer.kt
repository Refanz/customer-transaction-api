package com.refanzzzz.apicustomertransaction.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
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
    var name: String,
    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate", nullable = false)
    var birthdate: LocalDate,
    @Column(name = "birthplace", nullable = false)
    var birthplace: String,
): Auditable()