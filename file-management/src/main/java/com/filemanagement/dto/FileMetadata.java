package com.filemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileMetadata {

    private Long fileId;
    private String fileName;
    private Long fileSize;
    private Date fileCreatedAt;
    private String createdBy;
    private String contentType;
}