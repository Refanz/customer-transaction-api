package com.refanzzzz.apicustomertransaction.config

import com.refanzzzz.apicustomertransaction.security.SecurityAuditorAwareImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware

@Configuration
class AuditingConfiguration {
    @Bean
    fun auditorProvider(): AuditorAware<String> = SecurityAuditorAwareImpl()
}