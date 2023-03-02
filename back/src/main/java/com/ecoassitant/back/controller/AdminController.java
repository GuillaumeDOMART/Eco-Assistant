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

@RequestMapping("api/admin")
@RestController
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PatchMapping("ban")
    public ResponseEntity<Boolean> ban(@RequestBody BanDto banDto) {
        return ResponseEntity.ok(adminService.ban(banDto));
    }

    @PatchMapping("banProject")
    public ResponseEntity<Boolean> banProject(@RequestBody BanProjectDto banDto) {
        return ResponseEntity.ok(adminService.banProject(banDto));
    }
}
