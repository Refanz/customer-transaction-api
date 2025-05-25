package com.refanzzzz.apicustomertransaction.controller

import com.refanzzzz.apicustomertransaction.constant.Constant
import com.refanzzzz.apicustomertransaction.dto.request.*
import com.refanzzzz.apicustomertransaction.service.TransactionService
import com.refanzzzz.apicustomertransaction.util.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

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
        @RequestParam(required = false, name = "sort", defaultValue = "transactionDate") sortBy: String? = null,
        @RequestParam(required = false, name = "startDate") startDate: LocalDateTime? = null,
        @RequestParam(required = false, name = "endDate") endDate: LocalDateTime? = null,
        @RequestParam(required = false, name = "customerName") customerName: String? = null
    ): ResponseEntity<*> {
        val filterTransaction = TransactionFilterRequest(
            startDate = startDate,
            endDate = endDate,
            customerName = customerName
        )

        val response = transactionService.getAllTransactions(
            SearchingPagingSortingRequest(
                page = page,
                size = size,
                sortBy = sortBy
            ), filterTransaction
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

    @GetMapping("/reports/customer-spending-by-date")
    fun getCustomerSpendingByDate(
        @RequestParam startDate: LocalDateTime,
        @RequestParam endDate: LocalDateTime
    ): ResponseEntity<*> {
        val response = transactionService.getTotalAmountSpendByCustomerBetweenDatetime(startDate, endDate)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success get customer spending by date", response)
    }

    @GetMapping("reports/customer-spending")
    fun getCustomerSpending(): ResponseEntity<*> {
        val response = transactionService.getTotalAmountByCustomer()
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success get customer spending", response)
    }

    @GetMapping("/reports/product-spending")
    fun getProductSpending(): ResponseEntity<*> {
        val response = transactionService.getTotalAmountSpendByProduct()
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success get product spending", response)
    }
}