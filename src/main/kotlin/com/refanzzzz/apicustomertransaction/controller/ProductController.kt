package com.refanzzzz.apicustomertransaction.controller

import com.refanzzzz.apicustomertransaction.service.impl.ProductService
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val productService: ProductService
) {
    fun getAllProducts() {

    }
}