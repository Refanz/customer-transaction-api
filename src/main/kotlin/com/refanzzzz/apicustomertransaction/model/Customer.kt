package com.refanzzzz.apicustomertransaction.model

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
import java.time.LocalDateTime

@Entity
@Table(name = "m_customers")
@EntityListeners(AuditingEntityListener::class)
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,
    @Column(name = "name", nullable = false)
    val name: String,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birthdate", nullable = false)
    val birthdate: LocalDateTime,
    @Column(name = "birthplace", nullable = false)
    val birthplace: String,
): Auditable()