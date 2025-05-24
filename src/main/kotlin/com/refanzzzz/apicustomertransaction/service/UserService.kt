package com.refanzzzz.apicustomertransaction.service

import com.refanzzzz.apicustomertransaction.model.User
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.Optional

interface UserService : UserDetailsService {
    fun getUser(id: String): User
}