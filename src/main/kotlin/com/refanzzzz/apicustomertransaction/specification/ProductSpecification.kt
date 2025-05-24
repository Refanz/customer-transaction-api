package com.refanzzzz.apicustomertransaction.specification

import com.refanzzzz.apicustomertransaction.dto.request.SearchingPagingSortingRequest
import com.refanzzzz.apicustomertransaction.model.Product
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object ProductSpecification {
    fun getProductSpecification(request: SearchingPagingSortingRequest): Specification<Product> {
        val specification = Specification<Product> { root, query, criteriaBuilder ->
            val predicateList = mutableListOf<Predicate>()

            if (request.query != null) {
                val predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), request.query.lowercase() + "%")
                predicateList.add(predicate)
            }

            query.where(*predicateList.toTypedArray()).restriction
        }

        return specification
    }
}