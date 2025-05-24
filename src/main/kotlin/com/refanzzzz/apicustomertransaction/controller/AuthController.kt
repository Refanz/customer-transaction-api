package com.refanzzzz.apicustomertransaction.controller

import com.refanzzzz.apicustomertransaction.constant.Constant
import com.refanzzzz.apicustomertransaction.dto.request.LoginRequest
import com.refanzzzz.apicustomertransaction.service.AuthService
import com.refanzzzz.apicustomertransaction.util.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Constant.BASE_API)
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<*> {
        val loginResponse = authService.login(loginRequest)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Login successful", loginResponse)
    }
}