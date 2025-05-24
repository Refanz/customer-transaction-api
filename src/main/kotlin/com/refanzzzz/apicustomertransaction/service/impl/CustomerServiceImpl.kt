package com.refanzzzz.apicustomertransaction.service.impl

import com.refanzzzz.apicustomertransaction.constant.UserRole
import com.refanzzzz.apicustomertransaction.dto.request.CustomerCreatedRequest
import com.refanzzzz.apicustomertransaction.dto.request.CustomerUpdateRequest
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.response.CustomerResponse
import com.refanzzzz.apicustomertransaction.dto.response.ProfileResponse
import com.refanzzzz.apicustomertransaction.entity.Customer
import com.refanzzzz.apicustomertransaction.entity.User
import com.refanzzzz.apicustomertransaction.repository.CustomerRepository
import com.refanzzzz.apicustomertransaction.service.CustomerService
import com.refanzzzz.apicustomertransaction.service.UserService
import com.refanzzzz.apicustomertransaction.util.SortUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userService: UserService
) : CustomerService {

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
    override fun addCustomer(customerCreatedRequest: CustomerCreatedRequest): CustomerResponse {
        val userAccount = User(
            username = customerCreatedRequest.username,
            password = passwordEncoder.encode(customerCreatedRequest.password),
            email = customerCreatedRequest.email,
            roles = listOf(UserRole.ROLE_CUSTOMER)
        )

        userService.createUser(userAccount)

        val customer = Customer(
            name = customerCreatedRequest.name,
            birthdate = LocalDate.parse(customerCreatedRequest.birthdate),
            birthplace = customerCreatedRequest.birthplace,
            user = userAccount
        )

        customerRepository.saveAndFlush(customer)
        return mapToCustomerResponse(customer)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteCustomerById(id: String) {
        val customer = getCustomer(id)
        customerRepository.delete(customer)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateCustomer(
        id: String,
        customerRequest: CustomerUpdateRequest
    ): CustomerResponse {
        val customer = getCustomer(id)
        customer.name = customerRequest.name
        customer.birthplace = customerRequest.birthplace
        customer.birthdate = LocalDate.parse(customerRequest.birthdate)

        val updatedCustomer = customerRepository.saveAndFlush(customer)
        return mapToCustomerResponse(updatedCustomer)
    }

    override fun getMyProfile(): ProfileResponse {
        val userAccount = SecurityContextHolder.getContext().authentication.principal as User
        val customer = customerRepository.findByUser_Id(userAccount.id!!).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
        }

        return mapToProfileResponse(customer)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateMyProfile(customerUpdateRequest: CustomerUpdateRequest): ProfileResponse {
        val userAccount = SecurityContextHolder.getContext().authentication.principal as User
        val customer = customerRepository.findByUser_Id(userAccount.id!!).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
        }

        customer.name = customerUpdateRequest.name
        customer.birthplace = customerUpdateRequest.birthplace
        customer.birthdate = LocalDate.parse(customerUpdateRequest.birthdate)

        val updatedCustomer = customerRepository.saveAndFlush(customer)
        return mapToProfileResponse(updatedCustomer)
    }

    private fun mapToCustomerResponse(customer: Customer): CustomerResponse {
        return CustomerResponse(
            id = customer.id!!,
            name = customer.name!!,
            birthdate = customer.birthdate.toString(),
            birthplace = customer.birthplace!!,
            createdAt = customer.createdAt.toString(),
            updatedAt = customer.updatedAt.toString(),
            email = customer.user!!.email!!,
            username = customer.user!!.username!!,
            role = customer.user!!.roles!!.map { it.name }.toList()
        )
    }

    private fun mapToProfileResponse(customer: Customer): ProfileResponse {
        return ProfileResponse(
            id = customer.id!!,
            username = customer.user!!.username!!,
            name = customer.name!!,
            birthdate = customer.birthdate.toString(),
            birthplace = customer.birthplace!!,
            email = customer.user!!.email!!,
            role = customer.user!!.roles!!.map { it.name }.toList()
        )
    }
}