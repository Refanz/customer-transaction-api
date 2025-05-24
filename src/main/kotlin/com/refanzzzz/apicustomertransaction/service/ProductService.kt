package com.refanzzzz.apicustomertransaction.service

import com.refanzzzz.apicustomertransaction.dto.request.ProductRequest
import com.refanzzzz.apicustomertransaction.dto.response.ProductResponse
import org.springframework.data.domain.Page

interface ProductService {
    fun getAllProducts(request: ProductRequest): Page<ProductResponse>
    fun getProductById(id: String): ProductResponse
}