package com.ecoassitant.back.controller;

import com.ecoassitant.back.service.impl.AuthenticationService;
import com.ecoassitant.back.dto.*;
import com.ecoassitant.back.service.CalculService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for request about calcul
 */
@RequestMapping("api")
@RestController
public class CalculController {
    private final CalculService calculService;

    /**idProjet
     * Initalise the calculService
     * @param calculService composite for using Service methode
     */
    @Autowired
    public CalculController(CalculService calculService) {
        this.calculService = calculService;
    }

    /**
     * list of calcul for a resultat
     * @param projectId id of the resultat
     * @return list of calculs executed
     */
    @PostMapping("/calculs")
    public ResultatDto resultatsCalcul(@RequestBody IdDto projectId){
        return calculService.CalculsForProject(projectId.getId());
    }

    @RequestMapping("api/auth")
    @RestController
    public static class AuthenticationController {
        private final AuthenticationService authenticationService;
        @Autowired
        public AuthenticationController(AuthenticationService authenticationService) {
            this.authenticationService = authenticationService;
        }

        /**
         * Function to register a user
         *
         * @param registerInputDto input that represent the profile to create
         * @return Token authentication
         */
        @PostMapping("register")
        public ResponseEntity<TokenDto> register(@RequestBody RegisterInputDto registerInputDto){
            return authenticationService.register(registerInputDto);
        }

        /**
         * Function to logged user
         * @param authenticationInputDto input that represent the login form
         * @return Token authentication and the mail
         */
        @PostMapping("authentication")
        public AuthenticationOutPutDto authentication(@RequestBody AuthenticationInputDto authenticationInputDto){
            return authenticationService.authentication(authenticationInputDto);
        }

    }
}



