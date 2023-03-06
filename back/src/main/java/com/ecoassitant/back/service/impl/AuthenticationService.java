package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.EmailSenderService;
import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.AuthenticationInputDto;
import com.ecoassitant.back.dto.AuthenticationOutPutDto;
import com.ecoassitant.back.dto.RegisterInputDto;
import com.ecoassitant.back.dto.TokenDto;
import com.ecoassitant.back.entity.ProfilEntity;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.utils.StringGeneratorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<Boolean> register(RegisterInputDto registerInputDto) {
        if (profilRepository.findByMail(registerInputDto.getMail()).isPresent()) {
            return ResponseEntity.status(403).body(false);
        }
        String encodedPassword = passwordEncoder.encode(registerInputDto.getPassword());
        var profile = ProfilEntity.builder()
                .mail(registerInputDto.getMail())
                .password(registerInputDto.getPassword())
                .lastname(registerInputDto.getLastName())
                .firstname(registerInputDto.getFirstName())
                .isAdmin(-2)
                .build();

        var violations = validator.validate(profile);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        profile.setPassword(encodedPassword);

        profilRepository.save(profile);
        var token = jwtService.generateShortToken(profile);
        emailSenderService.sendEmail(registerInputDto.getMail(), "Eco-Assistant: Création de compte", "Voici le liens pour crée votre compte: https://" + domain + "/verifyMail?token=" + token);
        return ResponseEntity.ok(true);
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
            var randomMail = "guest" + "." + StringGeneratorUtils.generateRandomString(8) + "@eco-assistant-esipe.fr";
            if (profilRepository.findByMail(randomMail).isPresent()) {
                continue;
            }
            var encodedPassword = passwordEncoder.encode(StringGeneratorUtils.generateRandomPassword());
            var profile = ProfilEntity.builder()
                    .mail(randomMail)
                    .password(encodedPassword)
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
     * Function to send a mail for when the user forgot his password
     *
     * @param mail the mail of the user
     * @return if the mail was send
     */
    public ResponseEntity<Boolean> forgotMail(String mail) {
        var profile = profilRepository.findByMail(mail);
        if (profile.isEmpty()) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        var claims = new HashMap<String, Object>() {{
            put("verify", true);
        }};
        var token = jwtService.generateShortToken(profile.get(), claims);
        try {
            emailSenderService.sendEmail(mail, "Eco-Assistant: Mot de passe oublié", "Voici le liens pour changer vôtre mot de pass: https://" + domain + "/forgotPassword?token=" + token);
        } catch (MailException exception) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(true);
    }

    /**
     * Function to change password for a user with a token authentication
     *
     * @param token    Token of the current uset
     * @param password new password
     * @return if the password was change
     */
    public ResponseEntity<Boolean> changePasswordWithToken(String token, String password, String oldPassword) {
        token = token.substring(7);
        var mail = jwtService.extractMail(token);
        return changePassword(mail, password, oldPassword);
    }

    /**
     * Function to change password for a user
     *
     * @param mail     mail
     * @param password new password
     * @return if the password was change
     */
    public ResponseEntity<Boolean> changePassword(String mail, String password, String oldPassword) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            mail,
                            oldPassword
                    )
            );
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        var profil = profilRepository.findByMail(mail);
        if (profil.isEmpty()) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        var user = profil.get();
        var encodedPwd = passwordEncoder.encode(password);

        user.setPassword(password);
        var violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        user.setPassword(encodedPwd);
        profilRepository.save(user);
        return ResponseEntity.ok(true);
    }

    /**
     * Method to change the mail of the current user
     *
     * @param token   token of the current user
     * @param newMail new mail to change
     * @return the new token of the user based on the new mail
     */
    public ResponseEntity<TokenDto> changeMail(String token, String newMail) {
        var mail = jwtService.extractMail(token);
        var profil = profilRepository.findByMail(mail);
        if (profil.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (profilRepository.findByMail(newMail).isPresent()) {
            throw new DataIntegrityViolationException("L'adresse mail est déjà associé à un compte");
        }

        var user = profil.get();
        System.out.println("user = " + user);
        user.setMail(newMail);
        var violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        //Envoyer un nouveau mail pour verifier le mail plutôt de le changer ici
        var newToken = new TokenDto(jwtService.generateToken(user));
        profilRepository.save(user);

        return ResponseEntity.ok(newToken);
    }

    /**
     * Method to handle ${ConstraintViolationException} into an ${HttpStatus.BAD_REQUEST} When a validator find
     * violation for the entity and returns the field violated with a message
     *
     * @param ex exception
     * @return Map with the field error and the message
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Map<String, Map<String, String>> handleValidationExceptions(ConstraintViolationException ex) {
        var violations = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(error -> error.getPropertyPath().toString(), ConstraintViolation::getMessage));
        return Map.of("fieldErrors", violations);
    }

    /**
     * Method to handle DataIntegrityViolationException into an HttpStatus.BAD_REQUEST when you try to modify a
     * mail into an already existing one
     *
     * @param ex exception
     * @return Map with the field mail and the message
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public Map<String, String> handleDataViolationExceptions(DataIntegrityViolationException ex) {
        return Map.of("newMail", "L'adresse mail est déjà associé à un compte");
    }
}
