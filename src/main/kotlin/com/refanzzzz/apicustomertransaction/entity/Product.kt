package com.refanzzzz.apicustomertransaction.entity

import jakarta.persistence.*

@Entity
@Table(name = "m_products")
class Product(
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    val id: String? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "price", nullable = false)
    var price: Long? = null,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL])
    var taxDetails: MutableList<TaxDetail>? = null

) : Auditable()