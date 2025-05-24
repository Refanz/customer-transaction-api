package com.refanzzzz.apicustomertransaction.service.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.refanzzzz.apicustomertransaction.model.User
import com.refanzzzz.apicustomertransaction.service.JWTService
import com.refanzzzz.apicustomertransaction.util.JWTUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class JWTServiceImpl : JWTService {

    @Value("\${customer_transaction.jwt.secret}")
    private var jwtSecretKey: String? = null

    @Value("\${customer_transaction.jwt.expiration}")
    private var jwtExp: Long? = null

    @Value("\${customer_transaction.jwt.issuer}")
    private var jwtIssuer: String? = null

    override fun generateAccessToken(user: User): String {
        try {
            val algorithm = Algorithm.HMAC256(jwtSecretKey!!.toByteArray(StandardCharsets.UTF_8))
            return JWT.create()
                .withIssuer(jwtIssuer)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(jwtExp!!, ChronoUnit.MINUTES))
                .withSubject(user.id)
                .withClaim("role", user.roles.toString())
                .sign(algorithm)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error generating JWT token", e.cause)
        }
    }

    override fun extractTokenFromRequest(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION)
        return JWTUtil.parseJWTToken(bearerToken)
    }

    override fun extractClaimJWT(jwtToken: String): DecodedJWT? {
        try {
            val algorithm = Algorithm.HMAC256(jwtSecretKey!!.toByteArray(StandardCharsets.UTF_8))
            val verifier = JWT.require(algorithm).build()
            return verifier.verify(jwtToken)
        } catch (_: Exception) {
            return null
        }
    }

    override fun getUserID(token: String): String? {
        val decodedJWT = extractClaimJWT(token)
        if (decodedJWT != null) return decodedJWT.subject!!

        return null
    }
}