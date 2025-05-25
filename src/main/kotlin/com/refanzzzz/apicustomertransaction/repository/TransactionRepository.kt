package com.refanzzzz.apicustomertransaction.repository

import com.refanzzzz.apicustomertransaction.dto.report.CustomerSpendingReport
import com.refanzzzz.apicustomertransaction.dto.report.ProductReport
import com.refanzzzz.apicustomertransaction.entity.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TransactionRepository : JpaRepository<Transaction, String>, JpaSpecificationExecutor<Transaction> {
    @Query(
        """
        SELECT new com.refanzzzz.apicustomertransaction.dto.report.CustomerSpendingReport(t.customer.id, t.customer.name, SUM(t.totalAmountPaid)) 
            FROM Transaction t 
        WHERE t.transactionDate 
        BETWEEN :startDate AND :endDate
        GROUP BY t.customer.id, t.customer.name
        ORDER BY SUM(t.totalAmountPaid) DESC
    """
    )
    fun getTotalAmountSpendByCustomerBetweenDatetime(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<CustomerSpendingReport>

    @Query(
        """
            SELECT new com.refanzzzz.apicustomertransaction.dto.report.CustomerSpendingReport(t.customer.id, t.customer.name, SUM(t.totalAmountPaid))
                FROM Transaction t
            GROUP BY t.customer.id, t.customer.name
            ORDER BY SUM(t.totalAmountPaid) DESC
        """
    )
    fun getTotalAmountSpendByCustomer(): List<CustomerSpendingReport>

    @Query(
        """
            SELECT new com.refanzzzz.apicustomertransaction.dto.report.ProductReport(td.product.id, td.product.name, SUM(td.productGrandTotalAmount)) 
            FROM TransactionDetail td
            GROUP BY td.product.id, td.product.name
            ORDER BY SUM(td.productGrandTotalAmount) DESC
        """
    )
    fun getTotalAmountSpendByProduct(): List<ProductReport>
}
