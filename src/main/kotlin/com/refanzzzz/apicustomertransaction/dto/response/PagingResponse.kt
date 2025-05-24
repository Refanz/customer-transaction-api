package com.refanzzzz.apicustomertransaction.dto.response

data class PagingResponse(
    val totalPages: Int,
    val page: Int,
    val totalItems: Long,
    val size: Int
)
