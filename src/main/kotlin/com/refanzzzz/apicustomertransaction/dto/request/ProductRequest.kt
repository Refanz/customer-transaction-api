package com.refanzzzz.apicustomertransaction.dto.request

data class ProductRequest(
    val name: String? = null,
    val price: Long? = null
) : SearchingPagingSortingRequest()
