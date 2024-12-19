package com.filemanagement.controller;


import com.filemanagement.dto.PermissionDTO;
import com.filemanagement.model.PermissionLevel;
import com.filemanagement.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {


    private final PermissionService permissionService;

    @Operation(summary = "Create a new permission")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permission created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<PermissionDTO> createPermission(@Valid @RequestBody PermissionDTO permission) throws Exception {
        PermissionDTO createdPermission = permissionService.createPermission(permission);
        return ResponseEntity.status(201).body(createdPermission);
    }

    @Operation(summary = "Delete a permission")
    @DeleteMapping("/{userEmail}/{permissionLevel}")
    public ResponseEntity<Void> deletePermission(@PathVariable String userEmail, @PathVariable PermissionLevel permissionLevel) {
        permissionService.deletePermission(userEmail, permissionLevel);
        return ResponseEntity.noContent().build();
    }
}