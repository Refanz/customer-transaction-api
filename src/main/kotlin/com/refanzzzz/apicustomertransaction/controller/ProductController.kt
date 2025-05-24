package com.refanzzzz.apicustomertransaction.controller

import com.refanzzzz.apicustomertransaction.dto.request.ProductRequest
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.service.ProductService
import com.refanzzzz.apicustomertransaction.util.Constant
import com.refanzzzz.apicustomertransaction.util.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constant.PRODUCT_API)
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/{id}")
    fun getProductById(
        @PathVariable id: String
    ): ResponseEntity<*> {
        val productResponse = productService.getProductById(id)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success get product by id", productResponse)
    }

    @GetMapping
    fun getAllProducts(
        @RequestParam(required = false, name = "q") query: String? = null,
        @RequestParam(required = false, name = "page", defaultValue = "1") page: Int? = null,
        @RequestParam(required = false, name = "size", defaultValue = "10") size: Int? = null,
        @RequestParam(required = false, name = "sort", defaultValue = "id") sortBy: String? = null,
    ): ResponseEntity<*> {
        val pagingRequest = SearchingPagingSortingRequest(
            query = query,
            page = page,
            size = size,
            sortBy = sortBy
        )

        val productResponse = productService.getAllProducts(pagingRequest)
        return ResponseUtil.buildResponseWithPaging(
            httpStatus = HttpStatus.OK,
            message = "Success get all products",
            page = productResponse
        )
    }

    @PostMapping
    fun addNewProduct(
        @RequestBody productRequest: ProductRequest
    ): ResponseEntity<*> {
        val productResponse = productService.addProduct(productRequest)
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Success add new product", productResponse)
    }

    @DeleteMapping("/{id}")
    fun deleteProductById(
        @PathVariable id: String
    ): ResponseEntity<*> {
        productService.deleteProductById(id)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success delete product", null)
    }

    @PutMapping("/{id}")
    fun updateProductById(
        @PathVariable id: String,
        @RequestBody productRequest: ProductRequest
    ): ResponseEntity<*> {
        val productResponse = productService.updateProductById(id, productRequest)
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success update product", productResponse)
    }
}