package com.ecoassitant.back.config;

import com.ecoassitant.back.entity.ProfilEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * representation of user in the program
 */
public class UserDetailsImpl implements UserDetails {
    private final ProfilEntity profilEntity;

    /**
     * Create a UserDetailsImpl with a profilEntity
     * @param profilEntity the profilEntity
     */
    public UserDetailsImpl(ProfilEntity profilEntity) {
        this.profilEntity = profilEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(profilEntity.getIsAdmin() == 1){
            authorities.add(new SimpleGrantedAuthority("admin"));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return profilEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return profilEntity.getMail();
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
        return true;
    }
}
