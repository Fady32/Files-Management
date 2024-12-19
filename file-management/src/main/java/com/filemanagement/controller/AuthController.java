package com.filemanagement.controller;


import com.filemanagement.service.AuthService;
import com.filemanagement.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    private final PermissionService permissionService;

    @PostMapping("/login")
    public String login(@RequestParam String email) {
        return authService.authenticate(email);
    }

    @GetMapping("/permissions")
    public List<String> getPermissions(@RequestParam String email) {
        return permissionService.getPermissionsGroupPerUser(email);
    }
}