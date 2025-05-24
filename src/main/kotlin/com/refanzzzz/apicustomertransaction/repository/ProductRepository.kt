package com.refanzzzz.apicustomertransaction.repository

import com.refanzzzz.apicustomertransaction.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
}