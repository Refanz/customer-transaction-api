package com.refanzzzz.apicustomertransaction.controller

import com.refanzzzz.apicustomertransaction.constant.Constant
import com.refanzzzz.apicustomertransaction.constant.UserRole
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.request.UserCreatedRequest
import com.refanzzzz.apicustomertransaction.entity.User
import com.refanzzzz.apicustomertransaction.service.UserService
import com.refanzzzz.apicustomertransaction.util.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<*> {
        val user = userService.getUserById(id)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success get user by id", user)
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    fun getAllUsers(
        @RequestParam(required = false, name = "page", defaultValue = "1") page: Int? = null,
        @RequestParam(required = false, name = "size", defaultValue = "10") size: Int? = null,
        @RequestParam(required = false, name = "sort", defaultValue = "id") sortBy: String? = null,
    ): ResponseEntity<*> {
        val pagingRequest = SearchingPagingSortingRequest(
            page = page,
            size = size,
            sortBy = sortBy
        )
        val users = userService.getAllUsers(pagingRequest)
        return ResponseUtil.buildResponseWithPaging(HttpStatus.OK, "Success get all users", users)
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id: String): ResponseEntity<*> {
        userService.deleteUser(id)
        return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "User deleted successfully", null)
    }
}