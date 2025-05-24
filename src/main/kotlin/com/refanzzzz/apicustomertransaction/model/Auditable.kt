package com.refanzzzz.apicustomertransaction.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class Auditable(
    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: LocalDateTime? = null,
    @CreatedBy
    @Column(updatable = false)
    var createdBy: String? = null,
    @LastModifiedBy
    var updatedBy: String? = null
)