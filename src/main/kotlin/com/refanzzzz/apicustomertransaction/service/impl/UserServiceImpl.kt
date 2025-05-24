package com.refanzzzz.apicustomertransaction.service.impl

import com.refanzzzz.apicustomertransaction.constant.UserRole
import com.refanzzzz.apicustomertransaction.model.User
import com.refanzzzz.apicustomertransaction.repository.UserRepository
import com.refanzzzz.apicustomertransaction.service.UserService
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
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

        println(password!!)

        val userAccount = User(
            username = username!!,
            password = passwordEncoder.encode(password!!),
            email = email!!,
            roles = listOf(UserRole.ADMIN, UserRole.USER)
        )
        userRepository.saveAndFlush(userAccount)
    }

    override fun getUser(id: String): User {
        return userRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }
    }

    override fun loadUserByUsername(username: String?): UserDetails? {
        return userRepository.getUserByUsername(username!!).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }
    }
}