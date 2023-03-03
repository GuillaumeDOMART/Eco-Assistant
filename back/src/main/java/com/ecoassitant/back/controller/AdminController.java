package com.ecoassitant.back.controller;

import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.BanDto;
import com.ecoassitant.back.dto.profil.ProfilIdDto;
import com.ecoassitant.back.dto.project.ProjetDto;
import com.ecoassitant.back.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/admin")
@RestController
public class AdminController {

    private final AdminService adminService;
    private final JwtService jwtService;


    @Autowired
    public AdminController(AdminService adminService, JwtService jwtService) {
        this.adminService = adminService;
        this.jwtService = jwtService;
    }

    @PatchMapping("ban")
    public ResponseEntity<Boolean> ban(@RequestBody BanDto banDto) {
        return ResponseEntity.ok(adminService.ban(banDto));
    }

    /**
     * Endpoint to retrieve prjects by it's user's id
     */
    @GetMapping("/projects/user/{id}")
    @ResponseBody
    public ResponseEntity<List<ProjetDto>> getProjectsFinishedWithUserId(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") Integer profilId) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var projets = adminService.getProjectsFinishedFromUserId(mail,profilId);
        if(projets.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projets.get(),HttpStatus.OK);
    }
}
