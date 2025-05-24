package com.refanzzzz.apicustomertransaction.service.impl

import com.refanzzzz.apicustomertransaction.dto.request.PaymentRequest
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.response.PaymentResponse
import com.refanzzzz.apicustomertransaction.entity.Payment
import com.refanzzzz.apicustomertransaction.repository.PaymentRepository
import com.refanzzzz.apicustomertransaction.service.PaymentService
import com.refanzzzz.apicustomertransaction.util.SortUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class PaymentServiceImpl(private val paymentRepository: PaymentRepository) : PaymentService {

    @Transactional(readOnly = true)
    override fun getAllPayments(request: SearchingPagingSortingRequest): Page<PaymentResponse> {
        val sortBy = SortUtil.parseSort(request.sortBy!!)
        val pageable = PageRequest.of(request.pageNumber, request.size!!, sortBy)

        val paymentPage = paymentRepository.findAll(pageable)
        return paymentPage.map(this::mapToPaymentResponse)
    }

    @Transactional(readOnly = true)
    override fun getPaymentById(id: String): PaymentResponse {
        val payment = getPayment(id)
        return mapToPaymentResponse(payment)
    }

    @Transactional(readOnly = true)
    override fun getPayment(id: String): Payment {
        val payment = paymentRepository.findById(id)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found") }
        return payment
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun addPayment(paymentRequest: PaymentRequest): PaymentResponse {
        val payment = Payment(
            method = paymentRequest.method
        )

        val savedPayment = paymentRepository.saveAndFlush(payment)
        return mapToPaymentResponse(savedPayment)
    }


    @Transactional(rollbackFor = [Exception::class])
    override fun deletePaymentById(id: String) {
        val payment = getPayment(id)
        paymentRepository.delete(payment)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updatePaymentById(
        id: String,
        paymentRequest: PaymentRequest
    ): PaymentResponse {
        val payment = getPayment(id)
        payment.method = paymentRequest.method

        val updatedPayment = paymentRepository.saveAndFlush(payment)
        return mapToPaymentResponse(updatedPayment)
    }

    private fun mapToPaymentResponse(payment: Payment): PaymentResponse {
        return PaymentResponse(
            id = payment.id!!,
            method = payment.method!!,
            createdAt = payment.createdAt.toString(),
            updatedAt = payment.updatedAt.toString()
        )
    }
}