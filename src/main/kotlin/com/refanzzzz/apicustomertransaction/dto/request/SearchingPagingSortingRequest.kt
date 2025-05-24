package com.refanzzzz.apicustomertransaction.dto.request

open class SearchingPagingSortingRequest(
    val query: String? = null,
    val page: Int? = null,
    val size: Int? = null,
    val sortBy: String? = null,
) {
    fun getPage(): Int = if (page!! <= 0) 0 else page - 1
}
