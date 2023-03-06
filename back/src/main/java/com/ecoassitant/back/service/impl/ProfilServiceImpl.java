package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.ForgotPasswordVerifyDto;

import com.ecoassitant.back.dto.profil.ProfilDto;
import com.ecoassitant.back.dto.profil.ProfilIdDto;
import com.ecoassitant.back.dto.profil.ProfilSimplDto;
import com.ecoassitant.back.entity.ProfilEntity;
import com.ecoassitant.back.exception.NoSuchElementInDataBaseException;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.service.ProfilService;
import com.ecoassitant.back.utils.StringGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Implementation of ProfilService
 */
@Service
public class ProfilServiceImpl implements ProfilService {
    private final ProfilRepository repository;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    /**
     * Default constructor for ProfilServiceImpl
     */
    @Autowired
    public  ProfilServiceImpl(ProfilRepository repository, JwtService jwtService, AuthenticationService authenticationService){
        this.repository = repository;
        this.jwtService=jwtService;
        this.authenticationService = authenticationService;
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
        profilOwner.setFirstname(generateRandomString(8));
        profilOwner.setMail("guest"+generateRandomString(8)+"@eco-assistant-esipe.fr");
        profilOwner.setLastname(generateRandomString(8));
        profilOwner.setPassword(generateRandomString(8));
        profilOwner.setIsAdmin(-1);
        var savedEntity = repository.save(profilOwner);
        return Optional.of(new ProfilIdDto(savedEntity.getIdProfil()));
    }

    @Override
    public ProfilDto recoverProfilWithId(Integer id) {
        var profil =  repository.findById(id);
        if(profil.isEmpty()){
            throw new NoSuchElementInDataBaseException();
        }
        return new ProfilDto(profil.get());
    }

    @Override
    public ProfilDto recoverProfilWithMail(String mail) {
        var profil =  repository.findByMail(mail);
        if(profil.isEmpty()){
            throw new NoSuchElementInDataBaseException();
        }
        return new ProfilDto(profil.get());
    }

    @Override
    public ProfilDto recoverProfilWithToken(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var profil = repository.findByMail(mail);
        if(profil.isEmpty()){
            throw new NoSuchElementInDataBaseException();
        }
        return new ProfilDto(profil.get());

    }

    @Override
    public ResponseEntity<Boolean> forgotMail(String authorizationHeader, ForgotPasswordVerifyDto forgotPasswordVerifyDto) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        return authenticationService.changePassword(mail, forgotPasswordVerifyDto.getPassword(), forgotPasswordVerifyDto.getOldPassword());
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
    public ResponseEntity<Boolean> register(String token) {
        var mail = jwtService.extractMail(token);
        var profil = repository.findByMail(mail);
        if (profil.isPresent() && profil.get().getIsAdmin() == -2){
            var profilValue = profil.get();
            profilValue.setIsAdmin(0);
            repository.save(profilValue);
            ResponseEntity.ok(true);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
}
