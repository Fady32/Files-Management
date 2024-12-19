package com.filemanagement.mapper;

import com.filemanagement.dto.PermissionGroupDTO;
import com.filemanagement.model.PermissionGroup;
import org.springframework.beans.BeanUtils;

public class PermissionGroupMapper {

    public static PermissionGroupDTO fromEntityToDto(PermissionGroup permissionGroup) {
        PermissionGroupDTO dto = new PermissionGroupDTO();
        BeanUtils.copyProperties(permissionGroup, dto, "items");
        return dto;
    }

    public static PermissionGroup fromDtoToEntity(PermissionGroupDTO dto) {
        PermissionGroup permissionGroup = new PermissionGroup();
        BeanUtils.copyProperties(dto, permissionGroup);
        return permissionGroup;
    }
}
