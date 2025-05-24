package com.refanzzzz.apicustomertransaction.service

import com.auth0.jwt.interfaces.DecodedJWT
import com.refanzzzz.apicustomertransaction.model.User
import jakarta.servlet.http.HttpServletRequest

interface JWTService {
    fun generateAccessToken(user: User): String
    fun extractTokenFromRequest(request: HttpServletRequest): String
    fun extractClaimJWT(jwtToken: String): DecodedJWT?
    fun getUserID(token: String): String?
}