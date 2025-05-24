package com.refanzzzz.apicustomertransaction.model

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "m_customers")
@EntityListeners(AuditingEntityListener::class)
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,

    val name: String
)