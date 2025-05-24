package com.refanzzzz.apicustomertransaction.controller

import com.refanzzzz.apicustomertransaction.constant.Constant
import com.refanzzzz.apicustomertransaction.dto.request.PaymentRequest
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.service.PaymentService
import com.refanzzzz.apicustomertransaction.util.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constant.PAYMENT_API)
class PaymentController(private val paymentService: PaymentService) {

    @GetMapping
    fun getAllPayments(
        @RequestParam(required = false, name = "page", defaultValue = "1") page: Int? = null,
        @RequestParam(required = false, name = "size", defaultValue = "10") size: Int? = null,
        @RequestParam(required = false, name = "sort", defaultValue = "id") sortBy: String? = null,
    ): ResponseEntity<*> {

        val pagingRequest = SearchingPagingSortingRequest(
            page = page,
            size = size,
            sortBy = sortBy
        )

        val paymentResponse = paymentService.getAllPayments(pagingRequest)
        return ResponseUtil.buildResponseWithPaging(
            httpStatus = HttpStatus.OK,
            message = "Success get all payments",
            page = paymentResponse
        )
    }

    @GetMapping("/{id}")
    fun getPaymentById(
        @PathVariable id: String
    ): ResponseEntity<*> {
        val paymentResponse = paymentService.getPaymentById(id)
        return ResponseUtil.buildResponse(
            httpStatus = HttpStatus.OK,
            message = "Success get payment by id",
            data = paymentResponse
        )
    }

    @PostMapping
    fun addNewPayment(
        @RequestBody paymentRequest: PaymentRequest
    ): ResponseEntity<*> {
        val paymentResponse = paymentService.addPayment(paymentRequest)
        return ResponseUtil.buildResponse(
            httpStatus = HttpStatus.CREATED,
            message = "Success add new payment",
            data = paymentResponse
        )
    }

    @PutMapping("/{id}")
    fun updatePaymentById(
        @PathVariable id: String,
        @RequestBody paymentRequest: PaymentRequest
    ): ResponseEntity<*> {
        val paymentResponse = paymentService.updatePaymentById(id, paymentRequest)
        return ResponseUtil.buildResponse(
            httpStatus = HttpStatus.OK,
            message = "Success update payment by id",
            data = paymentResponse
        )
    }

    @DeleteMapping("/{id}")
    fun deletePaymentById(
        @PathVariable id: String
    ): ResponseEntity<*> {
        paymentService.deletePaymentById(id)
        return ResponseUtil.buildResponse(
            httpStatus = HttpStatus.NO_CONTENT,
            message = "Success delete payment by id",
            null
        )
    }
}