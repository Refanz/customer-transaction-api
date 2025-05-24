package com.refanzzzz.apicustomertransaction.service.impl

import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.request.TaxRequest
import com.refanzzzz.apicustomertransaction.dto.response.TaxResponse
import com.refanzzzz.apicustomertransaction.entity.Tax
import com.refanzzzz.apicustomertransaction.repository.TaxRepository
import com.refanzzzz.apicustomertransaction.service.TaxService
import com.refanzzzz.apicustomertransaction.util.SortUtil
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TaxServiceImpl(private val taxRepository: TaxRepository) : TaxService {

    @Transactional(readOnly = true)
    override fun getAllTaxes(request: SearchingPagingSortingRequest): Page<TaxResponse> {
        val sortBy = SortUtil.parseSort(request.sortBy!!)
        val pageable = PageRequest.of(request.pageNumber, request.size!!, sortBy)

        val response = taxRepository.findAll(pageable)
        return response.map(this::mapToTaxResponse)
    }

    @Transactional(readOnly = true)
    override fun getTax(id: String): Tax {
        val tax = taxRepository.findById(id)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Tax not found") }
        return tax
    }

    @Transactional(readOnly = true)
    override fun getTaxById(id: String): TaxResponse {
        val tax = getTax(id)
        return mapToTaxResponse(tax)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun addTax(taxRequest: TaxRequest): TaxResponse {
        val tax = Tax(
            name = taxRequest.name,
            rate = taxRequest.rate
        )

        val savedTax = taxRepository.saveAndFlush(tax)
        return mapToTaxResponse(savedTax)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteTaxById(id: String) {
        val tax = getTax(id)
        taxRepository.delete(tax)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateTaxById(
        id: String,
        taxRequest: TaxRequest
    ): TaxResponse {
        val tax = getTax(id).apply {
            name = taxRequest.name
            rate = taxRequest.rate
        }

        val updatedTax = taxRepository.saveAndFlush(tax)
        return mapToTaxResponse(updatedTax)
    }

    private fun mapToTaxResponse(tax: Tax): TaxResponse {
        return TaxResponse(
            id = tax.id!!,
            name = tax.name!!,
            rate = tax.rate!!,
            createdAt = tax.createdAt.toString(),
            updatedAt = tax.updatedAt.toString()
        )
    }
}