package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.EmailSenderService;
import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.ForgotPasswordVerifyDto;

import com.ecoassitant.back.dto.TokenDto;
import com.ecoassitant.back.dto.profil.ProfilDto;
import com.ecoassitant.back.dto.profil.ProfilIdDto;
import com.ecoassitant.back.dto.profil.ProfilSimplDto;
import com.ecoassitant.back.entity.ProfilEntity;
import com.ecoassitant.back.exception.NoSuchElementInDataBaseException;
import com.ecoassitant.back.exception.ViolationConnectionException;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.service.ProfilService;
import com.ecoassitant.back.utils.StringGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of ProfilService
 */
@Service
public class ProfilServiceImpl implements ProfilService {
    private final ProfilRepository repository;
    private final JwtService jwtService;
    private final ProfilRepository profilRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;
    private final EmailSenderService emailSenderService;

    @Value("${DOMAIN}")
    private String domain;

    /**
     * Default constructor for ProfilServiceImpl
     */
    @Autowired
    public  ProfilServiceImpl(ProfilRepository repository, JwtService jwtService, ProfilRepository profilRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, Validator validator, EmailSenderService emailSenderService){
        this.repository = repository;
        this.jwtService=jwtService;
        this.profilRepository = profilRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public ProfilDto getProfilByID(Integer id){
        var profil = repository.findById(id);
        return new ProfilDto(profil.orElseGet(null));
    }


    @Override
    public ProfilDto getProfilByMail(String mail) {

        var profil = repository.findByMail(mail);
        return new ProfilDto(profil.orElseThrow());
    }
    @Override
    public Optional<List<ProfilDto>> getAllUsersProfil(String mail) {
        var optionalProfil = repository.findByMail(mail);
        if(optionalProfil.isEmpty()){
            return Optional.empty();
        }
        var connectedProfil = optionalProfil.get();
        if(connectedProfil.getIsAdmin()!=1){
            return Optional.empty();
        }
        var profilUsersEntityList = repository.findByIsAdmin(0);
        var profilUsersDtoList = profilUsersEntityList.stream().map(ProfilDto::new).toList();
        return Optional.of(profilUsersDtoList);
    }
    @Override
    public Integer createProfil(ProfilSimplDto profilDto) {
        var profilEntity = new ProfilEntity();
        profilEntity.setMail(profilDto.getMail());
        profilEntity.setLastname(profilDto.getLastname());
        profilEntity.setFirstname(profilDto.getFirstname());
        profilEntity.setPassword(profilDto.getMdp());
        profilEntity.setIsAdmin(1);
        repository.save(profilEntity);

        var profil = repository.findByMail(profilDto.getMail()).orElseThrow();
        return profil.getIdProfil();
    }

    @Override
    public Optional<ProfilIdDto> deleteProfil(String mail) {
        var profilOwnerOpt = repository.findByMail(mail);
        if (profilOwnerOpt.isEmpty()) {
            return Optional.empty();
        }
       var profilOwner = profilOwnerOpt.get();
        if(profilOwner.getIsAdmin() < 0){
            throw new ViolationConnectionException();
        }
        profilOwner.setFirstname(generateRandomString(8));
        profilOwner.setMail("guest"+generateRandomString(8)+"@eco-assistant-esipe.fr");
        profilOwner.setLastname(generateRandomString(8));
        profilOwner.setPassword(generateRandomString(8));
        profilOwner.setIsAdmin(-1);
        var savedEntity = repository.save(profilOwner);
        return Optional.of(new ProfilIdDto(savedEntity.getIdProfil()));
    }

    @Override
    public ProfilDto recupererProfilAvecId(Integer id) {
        var profil =  repository.findById(id);
        if(profil.isEmpty()){
            throw new NoSuchElementInDataBaseException();
        }
        return new ProfilDto(profil.get());
    }

    @Override
    public ProfilDto recupererProfilAvecMail(String mail) {
        var profil =  repository.findByMail(mail);
        if(profil.isEmpty()){
            throw new NoSuchElementInDataBaseException();
        }
        return new ProfilDto(profil.get());
    }

    @Override
    public ProfilDto recupererProfilAvecToken(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var profil = repository.findByMail(mail);
        if(profil.isEmpty()){
            throw new NoSuchElementInDataBaseException();
        }
        return new ProfilDto(profil.get());

    }

    @Override
    public ResponseEntity<Boolean> forgotPasswordMail(String authorizationHeader, ForgotPasswordVerifyDto forgotPasswordVerifyDto) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        return changePassword(mail, forgotPasswordVerifyDto.getPassword());
    }

    /**
     * Function to generate a random string
     *
     * @param length the length of the random string
     * @return the random string
     */
    private static String generateRandomString(int length) {
        return StringGeneratorUtils.generateRandomString(length);
    }

    @Override
    public ResponseEntity<TokenDto> register(String token) {
        var mail = jwtService.extractMail(token);
        var profil = repository.findByMail(mail);
        if (profil.isPresent() && profil.get().getIsAdmin() == -2){
            var profilValue = profil.get();
            profilValue.setIsAdmin(0);
            repository.save(profilValue);
            var newToken  = jwtService.generateToken(profilValue);
            return ResponseEntity.ok(new TokenDto(newToken));
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
        return changePassword(mail, password);
    }

    /**
     * Function to change password for a user
     *
     * @param mail     mail
     * @param password new password
     * @return if the password was change
     */
    public ResponseEntity<Boolean> changePassword(String mail, String password) {
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
    public ResponseEntity<Boolean> changeMail(String token, String newMail) {
        var mail = jwtService.extractMail(token);
        var profil = profilRepository.findByMail(mail);
        if (profil.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (profilRepository.findByMail(newMail).isPresent()) {
            throw new DataIntegrityViolationException("L'adresse mail est déjà associé à un compte");
        }
        var user = profil.get();
        if(user.getIsAdmin() < 0){
            throw new ViolationConnectionException();
        }
        var violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        var tokenChangeMail = jwtService.generateShortTokenChangeMail(user,newMail);

        emailSenderService.sendEmail(newMail, "Eco-Assistant: Changement de mail", "Voici le lien pour changer votre mail : https://"+domain+"/modifyIDVerify?token="+tokenChangeMail);

        return ResponseEntity.ok(true);
    }

    /**
     * Method to change the mail of the current user
     *
     * @param token   token of the current user
     * @return the new token of the user based on the new mail
     */
    public ResponseEntity<TokenDto> changeMailVerify(String token) {
        var oldMail = jwtService.extractMail(token);
        var newMail = jwtService.extractNewMail(token);
        if(newMail == null){
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        var profil = profilRepository.findByMail(oldMail);

       if(profil.isEmpty()){
           throw new NoSuchElementInDataBaseException();
       }

       var user = profil.get();


       user.setMail(newMail);
       profilRepository.save(user);

        var newToken = new TokenDto(jwtService.generateToken(user));

        return ResponseEntity.ok(newToken);
    }
}
