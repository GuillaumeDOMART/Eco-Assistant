package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.AuthenticationInputDto;
import com.ecoassitant.back.dto.AuthenticationOutPutDto;
import com.ecoassitant.back.dto.RegisterInputDto;
import com.ecoassitant.back.dto.TokenDto;
import com.ecoassitant.back.entity.ProfilEntity;
import com.ecoassitant.back.repository.ProfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final ProfilRepository profilRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Function to register a user
     *
     * @param registerInputDto input that represent the profile to create
     * @return Token authentication
     */
    public ResponseEntity<AuthenticationOutPutDto> register(RegisterInputDto registerInputDto){
        if(profilRepository.findByMail(registerInputDto.getMail()).isPresent()){
            return ResponseEntity.status(403).body(null);
        }
        var profile = ProfilEntity.builder()
                .mail(registerInputDto.getMail())
                .password(passwordEncoder.encode(registerInputDto.getPassword()))
                .nom(registerInputDto.getNom())
                .prenom(registerInputDto.getPrenom())
                .isAdmin(0)
                .build();

        profilRepository.save(profile);
        var token = jwtService.generateToken(profile);
        return ResponseEntity.ok(new AuthenticationOutPutDto(profile.getIdProfil(), profile.getMail(), token));
    }

    /**
     * Function to logged user
     * @param authenticationInputDto input that represent the login form
     * @return Token authentication and the mail
     */
    public AuthenticationOutPutDto authentication(AuthenticationInputDto authenticationInputDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationInputDto.getLogin(),
                        authenticationInputDto.getPassword()
                )
        );
        var profile = profilRepository.findByMail(authenticationInputDto.getLogin()).orElseThrow();
        var token = jwtService.generateToken(profile);
        return new AuthenticationOutPutDto(profile.getIdProfil(), profile.getMail(),token);
    }

    public ResponseEntity<TokenDto> guest() {
        for(int i =0; i < 5; i++){
            var randomMail = "guest" + "." + generateRandomString(8)+"@eco-assistant-esipe.fr";
            if(profilRepository.findByMail(randomMail).isPresent()){
                continue;
            }
            var profile = ProfilEntity.builder()
                    .mail(randomMail)
                    .password("guest")
                    .nom("guest")
                    .prenom("guest")
                    .isAdmin(-1)
                    .build();

            profilRepository.save(profile);
            var token = jwtService.generateToken(profile);
            return ResponseEntity.ok(new TokenDto(token));
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    private static String generateRandomString(int length){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        var random = new Random();
        var sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            char randomChar = chars.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
