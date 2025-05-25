package com.refanzzzz.apicustomertransaction.dto.request

import java.time.LocalDateTime

data class TransactionFilterRequest(
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
    val customerName: String? = null
)