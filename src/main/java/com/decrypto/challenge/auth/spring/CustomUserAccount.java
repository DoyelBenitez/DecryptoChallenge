package com.decrypto.challenge.auth.spring;

import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomUserAccount implements CustomUserDetail {

    private String username;
    private String password;
    private String activeRol;
    private Boolean isEnabled;
    private Boolean isLocked;
    private Collection<? extends SimpleGrantedAuthority> authorities;

    public CustomUserAccount(UserAccountDTO userAccountDto) {
        this.isEnabled = true;
        this.isLocked = false;
        this.username = userAccountDto.getUsername();
        this.password = userAccountDto.getPassword();
        this.activeRol = userAccountDto.getRole().getName();
        this.authorities = this.getAuthorities(userAccountDto);
    }

    @Override
    public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    private Collection<? extends SimpleGrantedAuthority> getAuthorities(UserAccountDTO userAccountDTO) {
        List<SimpleGrantedAuthority> autorities = new ArrayList<>();
        autorities.add(new SimpleGrantedAuthority("ROLE_" + userAccountDTO.getRole().getName()));
        return autorities;
    }

}
