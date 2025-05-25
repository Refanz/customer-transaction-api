package com.refanzzzz.apicustomertransaction.dto.report

data class CustomerSpendingReport(
    var customerId: String?,
    var customerName: String?,
    var totalAmountPaid: Long?
)
