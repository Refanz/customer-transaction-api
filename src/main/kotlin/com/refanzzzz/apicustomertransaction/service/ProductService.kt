package com.refanzzzz.apicustomertransaction.service

import com.refanzzzz.apicustomertransaction.dto.request.ProductRequest
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.response.ProductResponse
import com.refanzzzz.apicustomertransaction.entity.Product
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
interface ProductService {
    fun getAllProducts(request: SearchingPagingSortingRequest): Page<ProductResponse>
    fun getProductById(id: String): ProductResponse
    fun getProduct(id: String): Product
    fun addProduct(productRequest: ProductRequest): ProductResponse
    fun deleteProductById(id: String)
    fun updateProductById(id: String, productRequest: ProductRequest): ProductResponse
}