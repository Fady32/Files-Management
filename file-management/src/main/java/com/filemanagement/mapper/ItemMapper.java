package com.filemanagement.mapper;

import com.filemanagement.dto.ItemDto;
import com.filemanagement.model.Item;
import org.springframework.beans.BeanUtils;

public class ItemMapper {

    public static ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        BeanUtils.copyProperties(item, dto);
        if (item.getParent() != null) {
            dto.setParentName(item.getParent().getName());
        }
        if (item.getPermissionGroup() != null) {
            dto.setPermissionGroupName(item.getPermissionGroup().getGroupName());
        }

        return dto;
    }
}
