package com.refanzzzz.apicustomertransaction.service.impl

import com.refanzzzz.apicustomertransaction.dto.request.CustomerRequest
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.response.CustomerResponse
import com.refanzzzz.apicustomertransaction.entity.Customer
import com.refanzzzz.apicustomertransaction.repository.CustomerRepository
import com.refanzzzz.apicustomertransaction.service.CustomerService
import com.refanzzzz.apicustomertransaction.util.SortUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class CustomerServiceImpl(private val customerRepository: CustomerRepository) : CustomerService {

    @Transactional(readOnly = true)
    override fun getAllCustomers(pagingRequest: SearchingPagingSortingRequest): Page<CustomerResponse> {
        val sortBy = SortUtil.parseSort(pagingRequest.sortBy ?: "")
        val pageable = PageRequest.of(pagingRequest.pageNumber, pagingRequest.size!!, sortBy)

        val customerPage = customerRepository.findAll(pageable)
        return customerPage.map(this::mapToCustomerResponse)
    }

    @Transactional(readOnly = true)
    override fun getCustomerById(id: String): CustomerResponse {
        val customer = getCustomer(id)
        return mapToCustomerResponse(customer)
    }

    @Transactional(readOnly = true)
    override fun getCustomer(id: String): Customer {
        val customer = customerRepository.findById(id)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found") }
        return customer
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun addCustomer(customerRequest: CustomerRequest): CustomerResponse {
        val customer = Customer(
            name = customerRequest.name,
            birthdate = LocalDate.parse(customerRequest.birthdate),
            birthplace = customerRequest.birthplace
        )

        val savedCustomer = customerRepository.saveAndFlush(customer)
        return mapToCustomerResponse(savedCustomer)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteCustomerById(id: String) {
        val customer = getCustomer(id)
        customerRepository.delete(customer)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateCustomer(
        id: String,
        customerRequest: CustomerRequest
    ): CustomerResponse {
        val customer = getCustomer(id)
        customer.name = customerRequest.name
        customer.birthplace = customerRequest.birthplace
        customer.birthdate = LocalDate.parse(customerRequest.birthdate)

        val updatedCustomer = customerRepository.saveAndFlush(customer)
        return mapToCustomerResponse(updatedCustomer)
    }

    private fun mapToCustomerResponse(customer: Customer): CustomerResponse {
        return CustomerResponse(
            id = customer.id!!,
            name = customer.name,
            birthdate = customer.birthdate.toString(),
            birthplace = customer.birthplace,
            createdAt = customer.createdAt.toString(),
            updatedAt = customer.updatedAt.toString()
        )
    }
}