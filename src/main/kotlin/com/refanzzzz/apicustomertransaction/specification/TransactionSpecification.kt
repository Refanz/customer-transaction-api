package com.refanzzzz.apicustomertransaction.specification

import com.refanzzzz.apicustomertransaction.constant.PaymentStatus
import com.refanzzzz.apicustomertransaction.entity.Customer
import com.refanzzzz.apicustomertransaction.entity.Payment
import com.refanzzzz.apicustomertransaction.entity.Transaction
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDateTime

object TransactionSpecification {
    fun byDateRange(startDate: LocalDateTime?, endDate: LocalDateTime?): Specification<Transaction>? {
        return Specification { root, query, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("transactionDate"), startDate))
            }

            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("transactionDate"), endDate))
            }

            if (predicates.isEmpty()) {
                null
            } else {
                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }
    }

    fun byCustomerName(customerName: String?): Specification<Transaction> {
        return Specification { root, query, criteriaBuilder ->
            if (customerName.isNullOrBlank()) {
                return@Specification null
            }
            val predicate = criteriaBuilder.like(
                criteriaBuilder.lower(root.join<Transaction, Customer>("customer").get("name")),
                "%${customerName.lowercase()}%"
            )
            criteriaBuilder.and(predicate)
        }
    }
}