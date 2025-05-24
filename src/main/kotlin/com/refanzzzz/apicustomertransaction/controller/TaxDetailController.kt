package com.refanzzzz.apicustomertransaction.controller

import com.refanzzzz.apicustomertransaction.constant.Constant
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.service.TaxDetailService
import com.refanzzzz.apicustomertransaction.util.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constant.TAX_API)
class TaxDetailController(private val taxDetailService: TaxDetailService) {

    @GetMapping("/details")
    fun getAllTaxDetails(
        @RequestParam(required = false, name = "page", defaultValue = "1") page: Int? = null,
        @RequestParam(required = false, name = "size", defaultValue = "10") size: Int? = null,
        @RequestParam(required = false, name = "sort", defaultValue = "id") sortBy: String? = null
    ): ResponseEntity<*> {
        val response = taxDetailService.getAllTaxDetails(
            SearchingPagingSortingRequest(
                page = page,
                size = size,
                sortBy = sortBy
            )
        )

        return ResponseUtil.buildResponseWithPaging(
            httpStatus = org.springframework.http.HttpStatus.OK,
            message = "Success get all tax details",
            page = response
        )
    }

    @GetMapping("/details/{id}")
    fun getTaxDetailById(
        @PathVariable id: String
    ): ResponseEntity<*> {
        val taxDetailResponse = taxDetailService.getTaxDetailById(id)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success get tax detail by id", taxDetailResponse)
    }

    @PostMapping("/details")
    fun addTaxDetail(
        @RequestBody taxDetailRequest: com.refanzzzz.apicustomertransaction.dto.request.TaxDetailRequest
    ): ResponseEntity<*> {
        val taxDetailResponse = taxDetailService.addTaxDetail(taxDetailRequest)
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Success add new tax detail", taxDetailResponse)
    }

    @DeleteMapping("/details/{id}")
    fun deleteTaxDetailById(
        @PathVariable id: String
    ): ResponseEntity<*> {
        taxDetailService.deleteTaxDetailById(id)
        return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "Success delete tax detail by id", null)
    }

    @PutMapping("/details/{id}")
    fun updateTaxDetailById(
        @PathVariable id: String,
        @RequestBody taxDetailRequest: com.refanzzzz.apicustomertransaction.dto.request.TaxDetailRequest
    ): ResponseEntity<*> {
        val taxDetailResponse = taxDetailService.updateTaxDetailById(id, taxDetailRequest)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success update tax detail by id", taxDetailResponse)
    }
}