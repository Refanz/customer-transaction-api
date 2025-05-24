package com.refanzzzz.apicustomertransaction.service

import com.refanzzzz.apicustomertransaction.entity.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    fun getUser(id: String): User
}