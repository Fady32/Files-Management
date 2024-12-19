package com.filemanagement.service;

import com.filemanagement.dto.FileMetadata;
import com.filemanagement.model.File;
import com.filemanagement.model.Item;
import com.filemanagement.repository.FileEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;


@RequiredArgsConstructor
@Service
public class FileService {
    private final FileEntityRepository fileEntityRepository;

    public void uploadFile(Item item, MultipartFile fileData, String userEmail) throws IOException {
        File file = new File();
        file.setBinaryData(fileData.getBytes());
        file.setItem(item);
        file.setCreatedAt(new Date());
        file.setCreatedBy(userEmail);
        file.setSize(fileData.getSize());
        file.setContentType(fileData.getContentType());
        fileEntityRepository.save(file);
    }


    /**
     * Fetch file metadata if the user has access.
     */
    public FileMetadata getFileMetadata(String itemId) {
        return fileEntityRepository.getFileMetadata(itemId);
    }

    public File downloadFile(Long itemId) throws Exception {
        return fileEntityRepository.findByItem_Id(itemId)
                .orElseThrow(() -> new Exception("File not found"));
    }
}