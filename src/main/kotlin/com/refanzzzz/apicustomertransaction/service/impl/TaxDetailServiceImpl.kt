package com.refanzzzz.apicustomertransaction.service.impl

import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.request.TaxDetailRequest
import com.refanzzzz.apicustomertransaction.dto.response.TaxDetailResponse
import com.refanzzzz.apicustomertransaction.entity.TaxDetail
import com.refanzzzz.apicustomertransaction.repository.TaxDetailRepository
import com.refanzzzz.apicustomertransaction.service.ProductService
import com.refanzzzz.apicustomertransaction.service.TaxDetailService
import com.refanzzzz.apicustomertransaction.service.TaxService
import com.refanzzzz.apicustomertransaction.util.SortUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class TaxDetailServiceImpl(
    private val taxDetailRepository: TaxDetailRepository,
    private val productService: ProductService,
    private val taxService: TaxService,
) : TaxDetailService {

    @Transactional(readOnly = true)
    override fun getAllTaxDetails(request: SearchingPagingSortingRequest): Page<TaxDetailResponse> {
        val sortBy = SortUtil.parseSort(request.sortBy!!)
        val pageable = PageRequest.of(request.pageNumber, request.size!!, sortBy)

        val taxDetailPage = taxDetailRepository.findAll(pageable)
        return taxDetailPage.map(this::mapToTaxDetailResponse)
    }

    @Transactional(readOnly = true)
    override fun getTaxDetailById(id: String): TaxDetailResponse {
        val taxDetail = getTaxDetail(id)
        return mapToTaxDetailResponse(taxDetail)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun addTaxDetail(taxDetailRequest: TaxDetailRequest): TaxDetailResponse {

        val tax = taxService.getTax(taxDetailRequest.taxId!!)
        val product = productService.getProduct(taxDetailRequest.productId!!)

        val taxDetail = TaxDetail(
            tax = tax,
            product = product,
            taxRateAtSale = tax.rate,
            taxAmount = product.price?.times(tax.rate!!)
        )

        val saved = taxDetailRepository.saveAndFlush(taxDetail)
        return mapToTaxDetailResponse(saved)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteTaxDetailById(id: String) {
        val taxDetail = getTaxDetail(id)
        taxDetailRepository.delete(taxDetail)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateTaxDetailById(
        id: String,
        taxDetailRequest: TaxDetailRequest
    ): TaxDetailResponse {
        val taxDetail = getTaxDetail(id)

        val tax = taxService.getTax(taxDetail.tax!!.id!!)
        val product = productService.getProduct(taxDetailRequest.productId!!)

        taxDetail.tax = tax
        taxDetail.product = product
        taxDetail.taxRateAtSale = tax.rate
        taxDetail.taxAmount = product.price?.times(tax.rate!!)

        val updatedTaxDetail = taxDetailRepository.saveAndFlush(taxDetail)
        return mapToTaxDetailResponse(updatedTaxDetail)
    }

    @Transactional(readOnly = true)
    override fun getTaxDetail(id: String): TaxDetail {
        val taxDetail = taxDetailRepository.findById(id)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Tax detail not found") }
        return taxDetail
    }

    private fun mapToTaxDetailResponse(taxDetail: TaxDetail): TaxDetailResponse {
        return TaxDetailResponse(
            id = taxDetail.id!!,
            createdAt = taxDetail.createdAt.toString(),
            updatedAt = taxDetail.updatedAt.toString(),
            productName = taxDetail.product!!.name!!
        )
    }
}