package com.refanzzzz.apicustomertransaction.service

import com.refanzzzz.apicustomertransaction.dto.report.CustomerSpendingReport
import com.refanzzzz.apicustomertransaction.dto.report.ProductReport
import com.refanzzzz.apicustomertransaction.dto.request.*
import com.refanzzzz.apicustomertransaction.dto.response.TransactionResponse
import com.refanzzzz.apicustomertransaction.entity.Transaction
import org.springframework.data.domain.Page
import java.time.LocalDateTime

interface TransactionService {
    fun getAllTransactions(request: SearchingPagingSortingRequest, filterRequest: TransactionFilterRequest): Page<TransactionResponse>
    fun getTransactionById(id: String): TransactionResponse
    fun deleteTransactionById(id: String)
    fun updateTransaction(id: String, transactionRequest: TransactionUpdateRequest): TransactionResponse
    fun getTransaction(id: String): Transaction
    fun createTransaction(transactionRequest: TransactionRequest): TransactionResponse
    fun addTransactionDetails(id: String, transactionDetailRequest: MutableList<TransactionDetailRequest>): TransactionResponse
    fun getTotalAmountSpendByCustomerBetweenDatetime(startDate: LocalDateTime, endDate: LocalDateTime): List<CustomerSpendingReport>
    fun getTotalAmountByCustomer(): List<CustomerSpendingReport>
    fun getTotalAmountSpendByProduct(): List<ProductReport>
}