package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.admin.BanDto;
import com.ecoassitant.back.dto.admin.BanProjectDto;
import com.ecoassitant.back.config.JwtService;
import com.ecoassitant.back.dto.project.ProjectDto;
import com.ecoassitant.back.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AdminController for endpoint for only admin
 */
@RequestMapping("api/admin")
@RestController
public class AdminController {

    private final AdminService adminService;
    private final JwtService jwtService;

    /**
     * Constructor for AdminController
     * @param adminService AdminService
     */
    @Autowired
    public AdminController(AdminService adminService, JwtService jwtService) {
        this.adminService = adminService;
        this.jwtService = jwtService;
    }

    /**
     * Function to ban a user
     * @param banDto Dto that contain the id of the user
     * @return if the ban is successful
     */
    @PatchMapping("ban")
    public ResponseEntity<Boolean> ban(@RequestBody BanDto banDto) {
        return ResponseEntity.ok(adminService.ban(banDto));
    }

    /**
     * Function to ban a project
     * @param banDto Dto that contain the id of the project
     * @return if the ban is successfully
     */
    @PatchMapping("banProject")
    public ResponseEntity<Boolean> banProject(@RequestBody BanProjectDto banDto) {
        System.out.println(banDto);
        return ResponseEntity.ok(adminService.banProject(banDto));
    }

    /**
     * Endpoint to retrieve projects by its user's id
     */
    @GetMapping("/projects/user/{id}")
    @ResponseBody
    public ResponseEntity<List<ProjectDto>> getProjectsFinishedWithUserId(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") Integer profileId) {
        String token = authorizationHeader.substring(7);
        var mail = jwtService.extractMail(token);
        var projets = adminService.getProjectsFinishedFromUserId(mail,profilId);
        if(projets.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projets.get(),HttpStatus.OK);
    }
}
