package com.ecoassitant.back.controller;

import com.ecoassitant.back.dto.admin.BanDto;
import com.ecoassitant.back.dto.admin.BanProjectDto;
import com.ecoassitant.back.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminController for endpoint for only admin
 */
@RequestMapping("api/admin")
@RestController
public class AdminController {

    private final AdminService adminService;

    /**
     * Constructor for AdminController
     * @param adminService AdminService
     */
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
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
        return ResponseEntity.ok(adminService.banProject(banDto));
    }
}
