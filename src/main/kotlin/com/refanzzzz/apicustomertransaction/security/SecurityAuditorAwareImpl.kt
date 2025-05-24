package com.refanzzzz.apicustomertransaction.security

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

class SecurityAuditorAwareImpl() : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            return Optional.empty()
        }

        return Optional.of(authentication.name)
    }
}