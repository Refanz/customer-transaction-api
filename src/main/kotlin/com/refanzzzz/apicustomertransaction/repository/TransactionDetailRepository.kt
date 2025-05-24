package com.refanzzzz.apicustomertransaction.repository

import com.refanzzzz.apicustomertransaction.entity.TransactionDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface TransactionDetailRepository : JpaRepository<TransactionDetailRepository, String>,
    JpaSpecificationExecutor<TransactionDetail> {
}