package com.filemanagement.dto;

import com.filemanagement.model.PermissionLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAuthorityDto {

    private PermissionLevel permissionName;

    private String groupName;

}
