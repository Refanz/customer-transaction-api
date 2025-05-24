package com.refanzzzz.apicustomertransaction.repository

import com.refanzzzz.apicustomertransaction.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CustomerRepository : JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {
    fun findByUser_Id(userId: String): Optional<Customer>
}