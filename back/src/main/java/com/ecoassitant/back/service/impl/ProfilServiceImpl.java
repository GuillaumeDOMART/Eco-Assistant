package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.ForgotPasswordVerifyDto;
import com.ecoassitant.back.dto.ProfilDto;
import com.ecoassitant.back.dto.ProfilIdDto;
import com.ecoassitant.back.dto.ProfilSimplDto;
import com.ecoassitant.back.entity.ProfilEntity;
import com.ecoassitant.back.exception.NoSuchElementInDataBase;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        profilOwner.setIsAdmin(0);
        var savedEntity = repository.save(profilOwner);
        return Optional.of(new ProfilIdDto(savedEntity.getIdProfil()));
    }

    @Override
    public ProfilDto recupererProfilAvecId(Integer id) {
        var profil =  repository.findById(id);
        if(profil.isEmpty()){
            throw new NoSuchElementInDataBase();
        }
        return new ProfilDto(profil.get());
    }

    @Override
    public ProfilDto recupererProfilAvecMail(String mail) {
        var profil =  repository.findByMail(mail);
        if(profil.isEmpty()){
            throw new NoSuchElementInDataBase();
        }
        return new ProfilDto(profil.get());
    }

    @Override
    public ProfilDto recupererProfilAvecToken(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var profil = repository.findByMail(mail);
        if(profil.isEmpty()){
            throw new NoSuchElementInDataBase();
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
