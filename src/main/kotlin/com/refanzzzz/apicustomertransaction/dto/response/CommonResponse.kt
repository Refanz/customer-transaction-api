package com.refanzzzz.apicustomertransaction.dto.response

data class CommonResponse<T>(
    val status: String,
    val message: String,
    val data: T,
    val paging: PagingResponse? = null
)