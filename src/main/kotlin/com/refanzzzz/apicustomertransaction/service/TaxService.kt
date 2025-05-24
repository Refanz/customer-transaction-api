package com.refanzzzz.apicustomertransaction.service

import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.request.TaxRequest
import com.refanzzzz.apicustomertransaction.dto.response.TaxResponse
import com.refanzzzz.apicustomertransaction.entity.Tax
import org.springframework.data.domain.Page

interface TaxService {
    fun getAllTaxes(request: SearchingPagingSortingRequest): Page<TaxResponse>
    fun getTax(id: String): Tax
    fun getTaxById(id: String): TaxResponse
    fun addTax(taxRequest: TaxRequest): TaxResponse
    fun deleteTaxById(id: String)
    fun updateTaxById(id: String, taxRequest: TaxRequest): TaxResponse
}