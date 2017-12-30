package pl.polsl.timetable.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(val user: User): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        //TODO: no idea
        return mutableListOf<GrantedAuthority>(SimpleGrantedAuthority("USER"))
    }

    override fun isEnabled() = true

    override fun getUsername() = user.email

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = user.passwordHash

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}