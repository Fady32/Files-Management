package com.filemanagement.mapper;

import com.filemanagement.dto.PermissionDTO;
import com.filemanagement.model.Permission;
import org.springframework.beans.BeanUtils;

public class PermissionMapper {

    public static PermissionDTO fromEntityToDto(Permission permission) {
        PermissionDTO dto = new PermissionDTO();
        BeanUtils.copyProperties(permission, dto, "permissionGroup");
        dto.setPermissionGroupId(permission.getPermissionGroup().getId());
        dto.setPermissionGroupName(permission.getPermissionGroup().getGroupName());
        return dto;
    }

    public static Permission fromDtoToEntity(PermissionDTO dto) {
        Permission permissionGroup = new Permission();
        BeanUtils.copyProperties(dto, permissionGroup);
        return permissionGroup;
    }
}
