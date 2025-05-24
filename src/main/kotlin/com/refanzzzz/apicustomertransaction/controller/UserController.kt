package com.refanzzzz.apicustomertransaction.controller

import com.refanzzzz.apicustomertransaction.constant.Constant
import com.refanzzzz.apicustomertransaction.constant.UserRole
import com.refanzzzz.apicustomertransaction.dto.request.UserCreatedRequest
import com.refanzzzz.apicustomertransaction.entity.User
import com.refanzzzz.apicustomertransaction.service.UserService
import com.refanzzzz.apicustomertransaction.util.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Constant.USER_API)
class UserController(private val userService: UserService) {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    fun createUser(
        @RequestBody userCreatedRequest: UserCreatedRequest
    ): ResponseEntity<*> {

        val listRoles = userCreatedRequest.role.map {
            UserRole.getRole(it)
        }

        val user = User(
            username = userCreatedRequest.username,
            password = userCreatedRequest.password,
            email = userCreatedRequest.email,
            roles = listRoles
        )

        userService.createUser(user)
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "User created successfully", null)
    }
}