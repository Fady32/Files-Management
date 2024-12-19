package com.filemanagement.controller;


import com.filemanagement.dto.PermissionGroupDTO;
import com.filemanagement.service.PermissionGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/permission-groups")
public class PermissionGroupController {

    private final PermissionGroupService permissionGroupService;

    @Operation(summary = "Create a new permission group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permission group created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<PermissionGroupDTO> createPermissionGroup(@RequestBody PermissionGroupDTO permissionGroup) {
        PermissionGroupDTO createdGroup = permissionGroupService.createPermissionGroup(permissionGroup);
        return ResponseEntity.status(201).body(createdGroup);
    }

    @Operation(summary = "Get all permission groups")
    @GetMapping
    public ResponseEntity<List<PermissionGroupDTO>> getAllPermissionGroups() {
        List<PermissionGroupDTO> groups = permissionGroupService.getAllPermissionGroups();
        return ResponseEntity.ok(groups);
    }

}