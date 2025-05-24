package com.refanzzzz.apicustomertransaction.entity

import com.refanzzzz.apicustomertransaction.constant.UserRole
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "m_user_accounts")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    @Column(name = "username", nullable = false, unique = true)
    private var username: String? = null,

    @Column(name = "password", nullable = false)
    private var password: String? = null,

    @Column(name = "email", nullable = false, unique = true)
    var email: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    var roles: List<UserRole>? = null
) : UserDetails, Auditable() {

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return roles!!.stream()
            .map { role: UserRole -> SimpleGrantedAuthority(role.name) }
            .toList()
    }

    override fun getPassword(): String? = this.password

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String? = this.username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
