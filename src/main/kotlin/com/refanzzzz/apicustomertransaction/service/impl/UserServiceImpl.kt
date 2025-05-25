package com.refanzzzz.apicustomertransaction.service.impl

import com.refanzzzz.apicustomertransaction.constant.UserRole
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.response.UserResponse
import com.refanzzzz.apicustomertransaction.entity.User
import com.refanzzzz.apicustomertransaction.repository.UserRepository
import com.refanzzzz.apicustomertransaction.service.UserService
import com.refanzzzz.apicustomertransaction.util.SortUtil
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) :
    UserService {
    @Value("\${customer_transaction.username-admin}")
    var username: String? = null

    @Value("\${customer_transaction.password-admin}")
    var password: String? = null

    @Value("\${customer_transaction.email-admin}")
    var email: String? = null

    @PostConstruct
    fun initUser() {
        val isExists = userRepository.existsUserByUsername(username!!)
        if (isExists) return

        val userAccount = User(
            username = username!!,
            password = passwordEncoder.encode(password!!),
            email = email!!,
            roles = listOf(UserRole.ROLE_ADMIN, UserRole.ROLE_USER)
        )
        userRepository.saveAndFlush(userAccount)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createUser(user: User): User {
        user.setPassword(passwordEncoder.encode(user.password))
        return userRepository.saveAndFlush(user)
    }

    @Transactional(readOnly = true)
    override fun getUser(id: String): User {
        return userRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }
    }

    @Transactional(readOnly = true)
    override fun getUserById(id: String): UserResponse {
        val user = getUser(id)
        return mapToUserResponse(user)
    }

    @Transactional(readOnly = true)
    override fun getAllUsers(request: SearchingPagingSortingRequest): Page<UserResponse> {
        val sortBy = SortUtil.parseSort(request.sortBy!!)
        val pageable = PageRequest.of(request.pageNumber, request.size!!, sortBy)

        val userPage = userRepository.findAll(pageable)
        return userPage.map(this::mapToUserResponse)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteUser(id: String) {
        val user = userRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }
        userRepository.delete(user)
    }

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String?): UserDetails? {
        return userRepository.getUserByUsername(username!!).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }
    }

    private fun mapToUserResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id!!,
            username = user.username!!,
            email = user.email!!,
            createdAt = user.createdAt.toString(),
            updatedAt = user.updatedAt.toString(),
            role = user.roles!!.map { it.name }.toList()
        )
    }
}