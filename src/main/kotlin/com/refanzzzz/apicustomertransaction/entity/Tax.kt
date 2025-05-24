package com.refanzzzz.apicustomertransaction.entity

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
    var id: String? = null,
    @Column(name = "name", nullable = false)
    var name: String? = null,
    @Column(name = "rate", nullable = false)
    var rate: Double? = null
): Auditable()
