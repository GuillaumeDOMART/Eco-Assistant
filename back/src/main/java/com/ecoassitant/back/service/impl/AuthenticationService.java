package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.EmailSenderService;
import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.AuthenticationInputDto;
import com.ecoassitant.back.dto.AuthenticationOutPutDto;
import com.ecoassitant.back.dto.RegisterInputDto;
import com.ecoassitant.back.dto.TokenDto;
import com.ecoassitant.back.entity.ProfilEntity;
import com.ecoassitant.back.repository.ProfilRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.*;

import javax.validation.ConstraintViolation;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for the Authentication
 */
@Service
@ControllerAdvice
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final ProfilRepository profilRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSenderService emailSenderService;

    private final Validator validator;
    @Value("${DOMAIN}")
    private String domain;

    /**
     * Function to register a user
     *
     * @param registerInputDto input that represent the profile to create
     * @return Token authentication
     */
    public ResponseEntity<TokenDto> register(RegisterInputDto registerInputDto) {
        if (profilRepository.findByMail(registerInputDto.getMail()).isPresent()) {
            return ResponseEntity.status(403).body(null);
        }
        String encodedPassword = passwordEncoder.encode(registerInputDto.getPassword());
        var profile = ProfilEntity.builder()
                .mail(registerInputDto.getMail())
                .password(registerInputDto.getPassword())
                .lastname(registerInputDto.getNom())
                .firstname(registerInputDto.getPrenom())
                .isAdmin(0)
                .build();

        var violations = validator.validate(profile);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        profile.setPassword(encodedPassword);

        profilRepository.save(profile);
        var token = jwtService.generateToken(profile);
        return ResponseEntity.ok(new TokenDto(token));
    }

    /**
     * Function to logged user
     *
     * @param authenticationInputDto input that represent the login form
     * @return Token authentication and the mail
     */
    public AuthenticationOutPutDto authentication(AuthenticationInputDto authenticationInputDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationInputDto.getLogin(),
                        authenticationInputDto.getPassword()
                )
        );
        var profile = profilRepository.findByMail(authenticationInputDto.getLogin()).orElseThrow();
        var token = jwtService.generateToken(profile);
        return new AuthenticationOutPutDto(profile.getMail(), token);
    }

    /**
     * Function to create au guest profile
     *
     * @return the token of the guest profile
     */
    public ResponseEntity<TokenDto> guest() {
        for (int i = 0; i < 5; i++) {
            var randomMail = "guest" + "." + generateRandomString(8) + "@eco-assistant-esipe.fr";
            if (profilRepository.findByMail(randomMail).isPresent()) {
                continue;
            }
            var profile = ProfilEntity.builder()
                    .mail(randomMail)
                    .password("guest")
                    .lastname("guest")
                    .firstname("guest")
                    .isAdmin(-1)
                    .build();

            profilRepository.save(profile);
            var token = jwtService.generateToken(profile);
            return ResponseEntity.ok(new TokenDto(token));
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Function to generate a random string
     *
     * @param length the length of the random string
     * @return the random string
     */
    private static String generateRandomString(int length) {
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

    /**
     * Function to send a mail for when the user forgot his password
     *
     * @param mail the mail of the user
     * @return if the mail was send
     */
    public boolean forgotMail(String mail) {
        var profile = profilRepository.findByMail(mail);
        if (profile.isEmpty()) {
            return false;
        }
        var claims = new HashMap<String, Object>() {{
            put("verify", true);
        }};
        var token = jwtService.generateToken(profile.get(), claims);
        emailSenderService.sendEmail(mail, "Eco-Assistant: Mot de pass oublié", "Voici le liens pour changer vôtre mot de pass: http://" + domain + "/forgot?token=" + token);
        return true;
    }

    /**
     * Function to change password for user
     *
     * @param mail     mail of the user
     * @param password new password
     * @return if the password was change
     */
    public ResponseEntity<Boolean> changePassword(String mail, String password) {
        var profil = profilRepository.findByMail(mail);
        if (profil.isEmpty()) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        var user = profil.get();
        user.setPassword(passwordEncoder.encode(password));
        profilRepository.save(user);
        return ResponseEntity.ok(true);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Map<String, Map<String, String>> handleValidationExceptions(ConstraintViolationException ex) {
        var violations = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(error -> error.getPropertyPath().toString(), ConstraintViolation::getMessage));
        return Map.of("fieldErrors", violations);
    }
}
