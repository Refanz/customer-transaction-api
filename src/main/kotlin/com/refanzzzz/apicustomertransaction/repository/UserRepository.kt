package com.refanzzzz.apicustomertransaction.repository

import com.refanzzzz.apicustomertransaction.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, String> {
    fun getUserByUsername(username: String): Optional<User>
    fun getUserByEmail(email: String): Optional<User>
    fun existsUserByUsername(username: String): Boolean
}