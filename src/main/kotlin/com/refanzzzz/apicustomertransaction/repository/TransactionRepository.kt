package com.refanzzzz.apicustomertransaction.repository

import com.refanzzzz.apicustomertransaction.entity.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository: JpaRepository<Transaction, String> {
}