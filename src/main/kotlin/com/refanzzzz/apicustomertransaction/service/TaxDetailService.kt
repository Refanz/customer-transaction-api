package com.refanzzzz.apicustomertransaction.service

import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.request.TaxDetailRequest
import com.refanzzzz.apicustomertransaction.dto.response.TaxDetailResponse
import com.refanzzzz.apicustomertransaction.entity.TaxDetail
import org.springframework.data.domain.Page

interface TaxDetailService {
    fun getAllTaxDetails(request: SearchingPagingSortingRequest): Page<TaxDetailResponse>
    fun getTaxDetailById(id: String): TaxDetailResponse
    fun addTaxDetail(taxDetailRequest: TaxDetailRequest): TaxDetailResponse
    fun deleteTaxDetailById(id: String)
    fun updateTaxDetailById(id: String, taxDetailRequest: TaxDetailRequest): TaxDetailResponse
    fun getTaxDetail(id: String): TaxDetail
}