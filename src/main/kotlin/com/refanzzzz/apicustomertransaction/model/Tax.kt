package com.refanzzzz.apicustomertransaction.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "m_taxes")
data class Tax(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "rate", nullable = false)
    val rate: Double
): Auditable()
