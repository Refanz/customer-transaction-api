package com.refanzzzz.apicustomertransaction.security

import com.refanzzzz.apicustomertransaction.service.JWTService
import com.refanzzzz.apicustomertransaction.service.UserService
import com.refanzzzz.apicustomertransaction.util.JWTUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JWTAuthenticationFilter(private val jwtService: JWTService, private val userService: UserService) :
    OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION)

            if (bearerToken != null) {
                val token = JWTUtil.parseJWTToken(bearerToken)

                val userId = jwtService.getUserID(token)
                val userAccount = userService.getUser(userId!!)
                val usernamePasswordAuthenticationToken =
                    UsernamePasswordAuthenticationToken(userAccount, null, userAccount.authorities)

                usernamePasswordAuthenticationToken.details = WebAuthenticationDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }

        } catch (e: Exception) {
            logger.error("JWT Authentication failed: ${e.message}", e)
        }

        filterChain.doFilter(request, response)
    }
}