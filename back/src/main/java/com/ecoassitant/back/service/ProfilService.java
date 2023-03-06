package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.ForgotPasswordVerifyDto;
import com.ecoassitant.back.dto.TokenDto;
import com.ecoassitant.back.dto.profil.ProfilDto;
import com.ecoassitant.back.dto.profil.ProfilSimplDto;
import com.ecoassitant.back.dto.profil.ProfilIdDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service for ProfilController
 */

public interface ProfilService {
    /**
     * Retrieve a profil by it id
     * @param id the profil id
     * @return a ProfilDto with the submitted ID, or null if it doesn't exist
     */
    ProfilDto getProfilByID(Integer id);

    /**
     * Retrieve a profil by it mail address
     * @param mail the profil mail
     * @return a ProfilDto with the submitted mail, or null if it doesn't exist
     */
    ProfilDto getProfilByMail(String mail);

    /**
     * Return all users if the user connected represents an admin
     * @param mail mail of the user connected
     * @return all users profiles
     */
    Optional<List<ProfilDto>> getAllUsersProfil(String mail);

    /**
     * Endpoint to create a user admin
     * @param profilDto profile to create
     * @return return the id of the profile
     */
    Integer createProfil(ProfilSimplDto profilDto);

    /**
     * Endpoint to anonymize a profile
     * @param mail is the mail of the user connected
     * @return optional the id of the profile dissociated
     */
    Optional<ProfilIdDto> deleteProfil(String mail);

    /**
     * Function to get profile with his id
     * @param id of the profile
     * @return the profile
     */
    ProfilDto recupererProfilAvecId(Integer id);

    /**
     * Function to get profile with his mail
     * @param mail of the profile
     * @return the profile
     */
    ProfilDto recupererProfilAvecMail(String mail);

    /**
     * Function to get profile with a token
     * @param authorizationHeader the token
     * @return the profile
     */
    ProfilDto recupererProfilAvecToken(String authorizationHeader);

    /**
     * Function to change password with a specific token
     * @param authorizationHeader the token
     * @param forgotPasswordVerifyDto the new password
     * @return if the change was successfully
     */
    ResponseEntity<Boolean> forgotPasswordMail(String authorizationHeader, ForgotPasswordVerifyDto forgotPasswordVerifyDto);

    /**
     * Function to finalize account creation
     *
     * @param token then token
     * @return is the creation was successfully
     */
    ResponseEntity<TokenDto> register(String token);

    /**
     * Function to change password for a user with a token authentication
     *
     * @param token    Token of the current uset
     * @param password new password
     * @return if the password was change
     */
    ResponseEntity<Boolean> changePasswordWithToken(String token, String password, String oldPassword);

    /**
     * Method to change the mail of the current user
     *
     * @param token   token of the current user
     * @param newMail new mail to change
     * @return the new token of the user based on the new mail
     */
    ResponseEntity<Boolean> changeMail(String token, String newMail);

    /**
     * Method to change the mail of the current user
     *
     * @param token   token of the current user
     * @return the new token of the user based on the new mail
     */
    ResponseEntity<TokenDto> changeMailVerify(String token);
}
