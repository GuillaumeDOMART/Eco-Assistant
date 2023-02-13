package com.ecoassitant.back.auth;

import com.ecoassitant.back.auth.config.JwtService;
import com.ecoassitant.back.dto.AuthenticationInputDto;
import com.ecoassitant.back.dto.AuthenticationOutPutDto;
import com.ecoassitant.back.dto.RegisterInputDto;
import com.ecoassitant.back.dto.TokenDto;
import com.ecoassitant.back.entity.ProfilEntity;
import com.ecoassitant.back.repository.ProfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final ProfilRepository profilRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenDto register(RegisterInputDto registerInputDto){
        var profil = ProfilEntity.builder()
                .mail(registerInputDto.getMail())
                .password(passwordEncoder.encode(registerInputDto.getPassword()))
                .nom(registerInputDto.getNom())
                .prenom(registerInputDto.getPrenom())
                .isAdmin(0)
                .build();

        profilRepository.save(profil);
        var token = jwtService.generateToken(profil);
        return new TokenDto(token);
    }

    public AuthenticationOutPutDto authentication(AuthenticationInputDto authenticationInputDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationInputDto.getLogin(),
                        authenticationInputDto.getPassword()
                )
        );
        var profil = profilRepository.findByMail(authenticationInputDto.getLogin()).orElseThrow();
        var token = jwtService.generateToken(profil);
        return new AuthenticationOutPutDto(profil.getMail(),token);
    }
}
