package com.refanzzzz.apicustomertransaction.repository

import com.refanzzzz.apicustomertransaction.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun getUserByUsername(username: String): Optional<User>
    fun getUserByEmail(email: String): Optional<User>
    fun existsUserByUsername(username: String): Boolean
}