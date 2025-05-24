package com.refanzzzz.apicustomertransaction.service

import com.refanzzzz.apicustomertransaction.dto.request.PaymentRequest
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.response.PaymentResponse
import com.refanzzzz.apicustomertransaction.entity.Payment
import org.springframework.data.domain.Page

interface PaymentService {
    fun getAllPayments(request: SearchingPagingSortingRequest): Page<PaymentResponse>
    fun getPaymentById(id: String): PaymentResponse
    fun getPayment(id: String): Payment
    fun addPayment(paymentRequest: PaymentRequest): PaymentResponse
    fun deletePaymentById(id: String)
    fun updatePaymentById(id: String, paymentRequest: PaymentRequest): PaymentResponse
}