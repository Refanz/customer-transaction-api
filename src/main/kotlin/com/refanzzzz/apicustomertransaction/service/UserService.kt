package com.refanzzzz.apicustomertransaction.service

import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.response.UserResponse
import com.refanzzzz.apicustomertransaction.entity.User
import org.springframework.data.domain.Page
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    fun createUser(user: User): User
    fun getUser(id: String): User
    fun getUserById(id: String): UserResponse
    fun getAllUsers(request: SearchingPagingSortingRequest): Page<UserResponse>
    fun deleteUser(id: String)
}