package com.refanzzzz.apicustomertransaction.controller

import com.refanzzzz.apicustomertransaction.constant.Constant
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.request.TransactionDetailRequest
import com.refanzzzz.apicustomertransaction.dto.request.TransactionRequest
import com.refanzzzz.apicustomertransaction.dto.request.TransactionUpdateRequest
import com.refanzzzz.apicustomertransaction.service.TransactionService
import com.refanzzzz.apicustomertransaction.util.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Constant.TRANSACTION_API)
class TransactionController(private val transactionService: TransactionService) {

    @PostMapping
    fun createTransaction(
        @RequestBody transactionRequest: TransactionRequest
    ): ResponseEntity<*> {
        val response = transactionService.createTransaction(transactionRequest)
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Success create transaction", response)
    }

    @PostMapping("/{id}/details")
    fun addTransactionDetails(
        @PathVariable id: String,
        @RequestBody transactionDetailRequest: MutableList<TransactionDetailRequest>
    ): ResponseEntity<*> {
        val response = transactionService.addTransactionDetails(id, transactionDetailRequest)
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Success add transaction details", response)
    }

    @GetMapping
    fun getAllTransactions(
        @RequestParam(required = false, name = "page", defaultValue = "1") page: Int? = null,
        @RequestParam(required = false, name = "size", defaultValue = "10") size: Int? = null,
        @RequestParam(required = false, name = "sort", defaultValue = "id") sortBy: String? = null,
    ): ResponseEntity<*> {
        val response = transactionService.getAllTransactions(
            SearchingPagingSortingRequest(
                page = page,
                size = size,
                sortBy = sortBy
            )
        )
        return ResponseUtil.buildResponseWithPaging(HttpStatus.OK, "Success get all transactions", response)
    }

    @GetMapping("/{id}")
    fun getTransactionById(
        @PathVariable id: String
    ): ResponseEntity<*> {
        val response = transactionService.getTransactionById(id)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success get transaction by id", response)
    }

    @DeleteMapping("/{id}")
    fun deleteTransactionById(
        @PathVariable id: String
    ): ResponseEntity<*> {
        transactionService.deleteTransactionById(id)
        return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "Success delete transaction by id", null)
    }

    @PutMapping("/{id}")
    fun updateTransaction(
        @PathVariable id: String,
        @RequestBody transactionRequest: TransactionUpdateRequest
    ): ResponseEntity<*> {
        val response = transactionService.updateTransaction(id, transactionRequest)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success update transaction by id", response)
    }
}