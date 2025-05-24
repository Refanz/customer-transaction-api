package com.refanzzzz.apicustomertransaction.dto.request

open class SearchingPagingSortingRequest(
    val query: String? = null,
    val page: Int? = null,
    val size: Int? = null,
    val sortBy: String? = null
) {
    val pageNumber: Int = page!!
        get() {
            return if (field <= 0) 0 else field - 1
        }
}
