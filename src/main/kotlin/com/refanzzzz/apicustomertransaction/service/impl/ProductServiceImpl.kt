package com.refanzzzz.apicustomertransaction.service.impl

import com.refanzzzz.apicustomertransaction.dto.request.ProductRequest
import com.refanzzzz.apicustomertransaction.dto.response.ProductResponse
import com.refanzzzz.apicustomertransaction.repository.ProductRepository
import com.refanzzzz.apicustomertransaction.service.ProductService
import org.springframework.data.domain.Page

class ProductServiceImpl(private val productRepository: ProductRepository): ProductService {
    override fun getAllProducts(request: ProductRequest): Page<ProductResponse> {
        TODO("Not yet implemented")
    }

    override fun getProductById(id: String): ProductResponse {
        TODO("Not yet implemented")
    }
}