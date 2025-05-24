package com.refanzzzz.apicustomertransaction.service.impl

import com.refanzzzz.apicustomertransaction.dto.request.LoginRequest
import com.refanzzzz.apicustomertransaction.dto.response.LoginResponse
import com.refanzzzz.apicustomertransaction.model.User
import com.refanzzzz.apicustomertransaction.repository.UserRepository
import com.refanzzzz.apicustomertransaction.service.AuthService
import com.refanzzzz.apicustomertransaction.service.JWTService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JWTService
) : AuthService {
    override fun login(loginRequest: LoginRequest): LoginResponse {
        val identifierType = if (loginRequest.identifier.contains("@")) "email" else "username"

        val user = when (identifierType) {
            "email" -> userRepository.getUserByEmail(loginRequest.identifier)
            "username" -> userRepository.getUserByUsername(loginRequest.identifier)
            else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid identifier type")
        }

        if (user.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                user.get().username,
                loginRequest.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication

        val userAccount = authentication.principal as User
        val accessToken = jwtService.generateAccessToken(userAccount)

        return LoginResponse(
            userAccount.roles.toString(),
            "Login successful",
            accessToken
        )
    }
}