package com.refanzzzz.apicustomertransaction.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
data class Product(
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    val id: String,
    val name: String
)