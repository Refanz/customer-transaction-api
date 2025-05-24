package com.refanzzzz.apicustomertransaction.constant

enum class PaymentStatus {
    PAID("paid"), NOT_PAID("not paid"), CANCELLED("cancelled");

    private var status: String

    constructor(status: String) {
        this.status = status
    }

    companion object {
        fun getStatusByValue(value: String) =
            PaymentStatus.entries.firstOrNull { it.status.lowercase() == value.lowercase() }
                ?: throw IllegalArgumentException()

        fun getStatusByName(name: String): String {
            val paymentStatus = entries.stream().filter { it.name == name }.findFirst()
            return paymentStatus.get().status
        }
    }
}