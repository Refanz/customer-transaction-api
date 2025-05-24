package com.refanzzzz.apicustomertransaction.controller

import com.refanzzzz.apicustomertransaction.constant.Constant
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.request.TaxRequest
import com.refanzzzz.apicustomertransaction.service.TaxService
import com.refanzzzz.apicustomertransaction.util.ResponseUtil
import io.swagger.v3.oas.annotations.parameters.RequestBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constant.TAX_API)
class TaxController(private val taxService: TaxService) {

    @GetMapping
    fun getAllTaxes(
        @RequestParam(required = false, name = "page", defaultValue = "1") page: Int? = null,
        @RequestParam(required = false, name = "size", defaultValue = "10") size: Int? = null,
        @RequestParam(required = false, name = "sort", defaultValue = "id") sortBy: String? = null,
    ): ResponseEntity<*> {
        val page = taxService.getAllTaxes(
            SearchingPagingSortingRequest(
                page = page,
                size = size,
                sortBy = sortBy
            )
        )

        return ResponseUtil.buildResponseWithPaging(HttpStatus.OK, "Success get all taxes", page)
    }

    @GetMapping("/{id}")
    fun getTaxById(
        @PathVariable id: String
    ): ResponseEntity<*> {
        val taxResponse = taxService.getTaxById(id)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success get tax by id", taxResponse)
    }

    @PostMapping
    fun addNewTax(
        @RequestBody taxRequest: TaxRequest
    ): ResponseEntity<*> {
        val taxResponse = taxService.addTax(taxRequest)
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Success add new tax", taxResponse)
    }

    @PutMapping("/{id}")
    fun updateTaxById(
        @PathVariable id: String,
        @RequestBody taxRequest: TaxRequest
    ): ResponseEntity<*> {
        val taxResponse = taxService.updateTaxById(id, taxRequest)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success update tax by id", taxResponse)
    }

    @DeleteMapping("/{id}")
    fun deleteTaxById(
        @PathVariable id: String
    ): ResponseEntity<*> {
        taxService.deleteTaxById(id)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success delete tax by id", null)
    }
}