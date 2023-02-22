package com.ecoassitant.back.config;

import com.ecoassitant.back.repository.ProfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class for the application config
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    /**
     * Bean for the UserDetail implementation
     * @param profilRepository profilRepository for the implementation of userDetail
     * @return the implementation UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(ProfilRepository profilRepository) {
        return new UserDetailsServiceImpl(profilRepository);
    }

    /**
     * Bean for the AuthenticationProvider
     * @param profilRepository profilRepository for the implementation of AuthenticationProvider
     * @return the implementation of AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider(ProfilRepository profilRepository) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService(profilRepository));
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Bean for the AuthenticationManager
     * @param config the config for the implementation AuthenticationManager
     * @return the implementation of AuthenticationManager
     * @throws Exception AuthenticationManager can throw exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Bean for the PasswordEncore
     * @return the implementation of passwordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
