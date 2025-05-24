package com.refanzzzz.apicustomertransaction.service

import com.refanzzzz.apicustomertransaction.dto.request.LoginRequest
import com.refanzzzz.apicustomertransaction.dto.response.LoginResponse

interface AuthService {
    fun login(loginRequest: LoginRequest): LoginResponse
}