package com.refanzzzz.apicustomertransaction.service

import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.request.TransactionDetailRequest
import com.refanzzzz.apicustomertransaction.dto.request.TransactionFilterRequest
import com.refanzzzz.apicustomertransaction.dto.request.TransactionRequest
import com.refanzzzz.apicustomertransaction.dto.request.TransactionUpdateRequest
import com.refanzzzz.apicustomertransaction.dto.response.TransactionDetailResponse
import com.refanzzzz.apicustomertransaction.dto.response.TransactionResponse
import com.refanzzzz.apicustomertransaction.entity.Transaction
import org.springframework.data.domain.Page

interface TransactionService {
    fun getAllTransactions(request: SearchingPagingSortingRequest, filterRequest: TransactionFilterRequest): Page<TransactionResponse>
    fun getTransactionById(id: String): TransactionResponse
    fun deleteTransactionById(id: String)
    fun updateTransaction(id: String, transactionRequest: TransactionUpdateRequest): TransactionResponse
    fun getTransaction(id: String): Transaction
    fun createTransaction(transactionRequest: TransactionRequest): TransactionResponse
    fun addTransactionDetails(id: String, transactionDetailRequest: MutableList<TransactionDetailRequest>): TransactionResponse
}