package com.ecoassitant.back.config;

import com.ecoassitant.back.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * representation of user service in the program
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ProfilRepository profilRepository;

    /**
     * Create a UserDetailsServiceImpl with a ProfilRepository
     * @param profilRepository the ProfilRepository
     */
    public UserDetailsServiceImpl(ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = profilRepository.findByMail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserDetailsImpl(user);
    }
}
