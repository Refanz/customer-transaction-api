package com.refanzzzz.apicustomertransaction

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
class ApiCustomerTransactionApplication

fun main(args: Array<String>) {
    runApplication<ApiCustomerTransactionApplication>(*args)
}
