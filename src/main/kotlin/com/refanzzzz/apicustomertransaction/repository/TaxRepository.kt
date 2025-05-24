package com.refanzzzz.apicustomertransaction.repository

import com.refanzzzz.apicustomertransaction.entity.Tax
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaxRepository : JpaRepository<Tax, String> {
}