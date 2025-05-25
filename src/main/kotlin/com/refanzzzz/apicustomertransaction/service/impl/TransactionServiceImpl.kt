package com.refanzzzz.apicustomertransaction.service.impl

import com.refanzzzz.apicustomertransaction.constant.PaymentStatus
import com.refanzzzz.apicustomertransaction.dto.request.*
import com.refanzzzz.apicustomertransaction.dto.response.TransactionDetailResponse
import com.refanzzzz.apicustomertransaction.dto.response.TransactionResponse
import com.refanzzzz.apicustomertransaction.entity.Transaction
import com.refanzzzz.apicustomertransaction.entity.TransactionDetail
import com.refanzzzz.apicustomertransaction.repository.TransactionDetailRepository
import com.refanzzzz.apicustomertransaction.repository.TransactionRepository
import com.refanzzzz.apicustomertransaction.service.*
import com.refanzzzz.apicustomertransaction.specification.TransactionSpecification
import com.refanzzzz.apicustomertransaction.util.SortUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class TransactionServiceImpl(
    private val transactionRepository: TransactionRepository,
    private val transactionDetailRepository: TransactionDetailRepository,
    private val customerService: CustomerService,
    private val paymentService: PaymentService,
    private val productService: ProductService,
    private val transactionPricingService: TransactionPricingService
) : TransactionService {

    @Transactional(rollbackFor = [Exception::class])
    override fun createTransaction(transactionRequest: TransactionRequest): TransactionResponse {
        val customer = customerService.getCustomer(transactionRequest.customerId)
        val payment = paymentService.getPayment(transactionRequest.paymentId)

        val transaction = Transaction(
            payment = payment,
            customer = customer,
            paymentStatus = PaymentStatus.NOT_PAID
        )

        val savedTransaction = transactionRepository.saveAndFlush(transaction)
        return mapToTransactionResponse(savedTransaction)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun addTransactionDetails(
        id: String,
        transactionDetailRequest: MutableList<TransactionDetailRequest>
    ): TransactionResponse {
        val transaction = getTransaction(id)

        transactionDetailRepository.saveAllAndFlush(
            transactionDetailRequest.map { detailRequest ->

                val product = productService.getProduct(detailRequest.productId)

                val transactionDetail = TransactionDetail(
                    transaction = transaction,
                    product = product,
                    priceAtTransaction = product.price,
                    quantity = detailRequest.quantity,
                    productSubtotalAmount = transactionPricingService.calculateProductSubtotal(
                        product.price!!,
                        detailRequest.quantity
                    )
                )

                val taxDetail = transactionDetail.product?.taxDetails ?: emptyList()
                taxDetail.map { tax ->
                    val taxRate = tax.taxRateAtSale
                    transactionDetail.productTotalTaxAmount = transactionDetail.productTotalTaxAmount?.plus(
                        transactionPricingService.calculateProductTotalTaxAmount(
                            transactionDetail.productSubtotalAmount!!,
                            taxRate!!
                        )
                    )
                }

                transactionDetail.productGrandTotalAmount =
                    transactionDetail.productSubtotalAmount?.plus(transactionDetail.productTotalTaxAmount!!)

                transactionDetail
            }
        )

        return mapToTransactionResponse(transaction)
    }

    @Transactional(readOnly = true)
    override fun getAllTransactions(
        request: SearchingPagingSortingRequest,
        filterRequest: TransactionFilterRequest
    ): Page<TransactionResponse> {

        var specifications: Specification<Transaction> = Specification.where(null)

        TransactionSpecification.byDateRange(filterRequest.startDate, filterRequest.endDate).let {
            specifications = specifications.and(it)
        }

        TransactionSpecification.byCustomerName(filterRequest.customerName).let {
            specifications = specifications.and(it)
        }

        TransactionSpecification.byPaymentStatus(filterRequest.paymentStatus).let {
            specifications = specifications.and(it)
        }

        TransactionSpecification.byPaymentMethod(filterRequest.paymentMethod).let {
            specifications = specifications.and(it)
        }


        val sortBy = SortUtil.parseSort(request.sortBy!!)
        val pageable = PageRequest.of(request.pageNumber, request.size!!, sortBy)

        val page = transactionRepository.findAll(specifications, pageable)

        return page.map(this::mapToTransactionResponse)
    }

    @Transactional(readOnly = true)
    override fun getTransactionById(id: String): TransactionResponse {
        val transaction = getTransaction(id)
        return mapToTransactionResponse(transaction)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteTransactionById(id: String) {
        val transaction = getTransaction(id)
        transactionRepository.delete(transaction)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateTransaction(
        id: String,
        transactionRequest: TransactionUpdateRequest
    ): TransactionResponse {
        val transaction = getTransaction(id)

        val paymentMethod = if (transactionRequest.paymentId != null) {
            paymentService.getPayment(transactionRequest.paymentId!!)
        } else {
            transaction.payment
        }

        transaction.apply {
            paymentStatus = PaymentStatus.getStatusByValue(transactionRequest.paymentStatus!!)
            payment = paymentMethod
        }

        val updatedTransaction = transactionRepository.saveAndFlush(transaction)
        return mapToTransactionResponse(updatedTransaction)
    }

    @Transactional(readOnly = true)
    override fun getTransaction(id: String): Transaction {
        val transaction = transactionRepository.findById(id)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found") }
        return transaction
    }

    private fun mapToTransactionResponse(transaction: Transaction): TransactionResponse {
        return TransactionResponse(
            id = transaction.id!!,
            customerName = transaction.customer!!.name!!,
            paymentMethod = transaction.payment!!.method!!,
            paymentStatus = PaymentStatus.getStatusByName(transaction.paymentStatus!!.name),
            createdAt = transaction.createdAt.toString(),
            updatedAt = transaction.updatedAt.toString(),
            transactionDate = transaction.transactionDate.toString(),

            transactionDetails = transaction.transactionDetails?.map { detail ->
                TransactionDetailResponse(
                    id = detail.id!!,
                    productName = detail.product!!.name!!,
                    quantity = detail.quantity!!,
                    priceAtTransaction = detail.priceAtTransaction!!,
                    productSubtotalAmount = detail.productSubtotalAmount!!,
                    productGrandTotalAmount = detail.productGrandTotalAmount!!,
                    productTotalTaxAmount = detail.productTotalTaxAmount!!
                )
            }?.toMutableList() ?: emptyList<TransactionDetailResponse>().toMutableList()
        )
    }
}