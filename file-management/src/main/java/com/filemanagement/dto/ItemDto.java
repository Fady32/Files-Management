package com.filemanagement.dto;


import com.filemanagement.model.ItemType;
import lombok.Data;

@Data
public class ItemDto {

    private String name;

    private String permissionGroupName;

    private ItemType type;

    private String parentId;

    private String parentName;
}

