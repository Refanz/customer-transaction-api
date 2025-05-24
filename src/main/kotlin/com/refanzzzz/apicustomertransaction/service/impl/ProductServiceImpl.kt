package com.refanzzzz.apicustomertransaction.service.impl

import com.refanzzzz.apicustomertransaction.dto.request.ProductRequest
import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.dto.response.ProductResponse
import com.refanzzzz.apicustomertransaction.dto.response.TaxProductResponse
import com.refanzzzz.apicustomertransaction.entity.Product
import com.refanzzzz.apicustomertransaction.repository.ProductRepository
import com.refanzzzz.apicustomertransaction.service.ProductService
import com.refanzzzz.apicustomertransaction.specification.ProductSpecification
import com.refanzzzz.apicustomertransaction.util.SortUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {

    @Transactional(readOnly = true)
    override fun getAllProducts(request: SearchingPagingSortingRequest): Page<ProductResponse> {
        val sortBy = SortUtil.parseSort(request.sortBy!!)
        val pageable = PageRequest.of(request.pageNumber, request.size!!, sortBy)
        val specification = ProductSpecification.getProductSpecification(request)

        val productPage = productRepository.findAll(specification, pageable)
        return productPage.map(this::mapToProductResponse)
    }

    override fun getProductById(id: String): ProductResponse {
        val product = getProduct(id)
        return mapToProductResponse(product)
    }

    override fun getProduct(id: String): Product {
        val product = productRepository.findById(id)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found") }

        return product
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun addProduct(productRequest: ProductRequest): ProductResponse {
        val product = Product(
            name = productRequest.name,
            price = productRequest.price
        )

        val savedProduct = productRepository.saveAndFlush(product)
        return mapToProductResponse(savedProduct)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteProductById(id: String) {
        val product = getProduct(id)
        productRepository.delete(product)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateProductById(
        id: String,
        productRequest: ProductRequest
    ): ProductResponse {
        val product = getProduct(id)
        product.name = productRequest.name
        product.price = productRequest.price

        val updatedProduct = productRepository.saveAndFlush(product)
        return mapToProductResponse(updatedProduct)
    }

    private fun mapToProductResponse(product: Product): ProductResponse {
        return ProductResponse(
            id = product.id!!,
            name = product.name!!,
            price = product.price!!,
            createdAt = product.createdAt.toString(),
            updatedAt = product.updatedAt.toString(),

            taxDetails = product.taxDetails?.map { taxDetail ->
                TaxProductResponse(
                    id = taxDetail.id!!,
                    taxAmount = taxDetail.taxAmount!!.toLong(),
                    taxRate = taxDetail.taxRateAtSale!!,
                    taxName = taxDetail.tax!!.name!!,
                )
            }?.toMutableList() ?: emptyList<TaxProductResponse>().toMutableList()
        )
    }
}