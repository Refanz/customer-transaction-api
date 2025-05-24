package com.refanzzzz.apicustomertransaction.controller

import com.refanzzzz.apicustomertransaction.constant.Constant
import com.refanzzzz.apicustomertransaction.dto.request.CustomerRequest
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.service.CustomerService
import com.refanzzzz.apicustomertransaction.util.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Constant.CUSTOMER_API)
class CustomerController(private val customerService: CustomerService) {

    @GetMapping
    fun getAllCustomers(
        @RequestParam(required = false, name = "page", defaultValue = "1") page: Int? = null,
        @RequestParam(required = false, name = "size", defaultValue = "10") size: Int? = null,
        @RequestParam(required = false, name = "sort", defaultValue = "id") sortBy: String? = null,
    ): ResponseEntity<*> {
        val pagingRequest = SearchingPagingSortingRequest(
            page = page,
            size = size,
            sortBy = sortBy
        )

        val customerResponse = customerService.getAllCustomers(pagingRequest)
        return ResponseUtil.buildResponseWithPaging(
            httpStatus = HttpStatus.OK,
            message = "Success get all customers",
            page = customerResponse
        )
    }

    @GetMapping("/{id}")
    fun getCustomerById(
        @PathVariable id: String
    ): ResponseEntity<*> {
        val customerResponse = customerService.getCustomerById(id)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success get customer by id", customerResponse)
    }

    @PostMapping
    fun addNewCustomer(
        @RequestBody customerRequest: CustomerRequest
    ): ResponseEntity<*> {
        val customerResponse = customerService.addCustomer(customerRequest)
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Success add new customer", customerResponse)
    }

    @DeleteMapping("/{id}")
    fun deleteCustomerById(
        @PathVariable id: String
    ): ResponseEntity<*> {
        customerService.deleteCustomerById(id)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success delete customer by id", null)
    }

    @PutMapping("/{id}")
    fun updateCustomerById(
        @PathVariable id: String,
        @RequestBody customerRequest: CustomerRequest
    ): ResponseEntity<*> {
        val customerResponse = customerService.updateCustomer(id, customerRequest)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success update customer by id", customerResponse)
    }
}