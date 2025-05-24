package com.refanzzzz.apicustomertransaction.service

import com.refanzzzz.apicustomertransaction.dto.request.CustomerCreatedRequest
import com.refanzzzz.apicustomertransaction.dto.request.CustomerUpdateRequest
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.response.CustomerResponse
import com.refanzzzz.apicustomertransaction.dto.response.ProfileResponse
import com.refanzzzz.apicustomertransaction.entity.Customer
import org.springframework.data.domain.Page

interface CustomerService {
    fun getAllCustomers(pagingRequest: SearchingPagingSortingRequest): Page<CustomerResponse>
    fun getCustomerById(id: String): CustomerResponse
    fun getCustomer(id: String): Customer
    fun addCustomer(customerCreatedRequest: CustomerCreatedRequest): CustomerResponse
    fun deleteCustomerById(id: String)
    fun updateCustomer(id: String, customerRequest: CustomerUpdateRequest): CustomerResponse
    fun getMyProfile(): ProfileResponse
    fun updateMyProfile(customerUpdateRequest: CustomerUpdateRequest): ProfileResponse
//    fun updateCustomerAccount(customerUpdateAccountRequest: CustomerUpdateAccountRequest): CustomerResponse
}