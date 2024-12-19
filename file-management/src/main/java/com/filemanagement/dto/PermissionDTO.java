package com.filemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PermissionDTO {
    // Validate email format
    @NotNull(message = "User email must not be null")
    @Email(message = "User email must be a valid email address")
    private String userEmail;

    // Validate the permission level with a custom error message
    @NotNull(message = "Permission level must not be null")
    @Pattern(regexp = "^(EDIT|VIEW|DELETE)$", message = "Permission level must be one of [EDIT, VIEW, DELETE]")
    private String permissionLevel;

    @Null
    private Long permissionGroupId;

    // Validate permissionGroupName with a custom pattern if needed
    @NotNull(message = "Permission group name must not be null")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Permission group name can only contain alphanumeric characters and spaces")
    private String permissionGroupName;
}