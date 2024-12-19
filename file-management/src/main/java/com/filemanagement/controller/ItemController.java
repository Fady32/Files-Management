package com.filemanagement.controller;

import com.filemanagement.dto.FileMetadata;
import com.filemanagement.dto.ItemDto;
import com.filemanagement.model.File;
import com.filemanagement.model.ItemType;
import com.filemanagement.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Operation(summary = "Create a Document", description = "Create a document (space).")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Document created successfully"), @ApiResponse(responseCode = "400", description = "Bad request, invalid data or permission issue"), @ApiResponse(responseCode = "401", description = "Unauthorized access")})
    @PostMapping("/create-space")
    public ResponseEntity<ItemDto> createSpace(@RequestParam String spaceName, @RequestParam String permissionGroupName) {
        ItemDto item = itemService.createItem(spaceName, ItemType.SPACE, permissionGroupName, null, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @Operation(summary = "Create a folder", description = "Create a folder (folder).")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Folder created successfully"), @ApiResponse(responseCode = "400", description = "Bad request, invalid data or permission issue"), @ApiResponse(responseCode = "401", description = "Unauthorized access")})
    @PostMapping("/create-folder/{spaceName}")
    public ResponseEntity<ItemDto> createFolder(@PathVariable String spaceName, @RequestParam String folderName) {
        ItemDto item = itemService.createItem(folderName, ItemType.FOLDER, null, spaceName, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);

    }


    @Operation(summary = "Upload a file", description = "Upload a file to a specific item (folder or space).")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "File uploaded successfully"), @ApiResponse(responseCode = "400", description = "Bad request, invalid file data"), @ApiResponse(responseCode = "401", description = "Unauthorized access")})
    @PostMapping(value = "/create-file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam String spaceName, @RequestParam(required = false) String folderName, @RequestParam("file") @Parameter(description = "The file to be uploaded") MultipartFile file) {
        ItemDto item = itemService.createItem(folderName, ItemType.FILE, null, spaceName, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }


    @Operation(summary = "Get file metadata", description = "Fetch metadata of a file based on its ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "File metadata retrieved successfully"), @ApiResponse(responseCode = "404", description = "File not found"), @ApiResponse(responseCode = "401", description = "Unauthorized access")})
    @GetMapping("/file/getMetaData")
    public ResponseEntity<?> getFileMetadataPerUser(@RequestParam String space, @RequestParam(required = false) String folder, @RequestParam String fileName) throws Exception {

        FileMetadata fileMetadata = itemService.getFileMetadata(space, folder, fileName);
        return ResponseEntity.ok(fileMetadata);
    }


    @Operation(summary = "Download file", description = "Download a file based on its ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "File downloaded successfully"), @ApiResponse(responseCode = "404", description = "File not found"), @ApiResponse(responseCode = "401", description = "Unauthorized access")})
    @GetMapping("/file/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String space, @RequestParam(required = false) String folder, @RequestParam String fileName) throws Exception {
        File fileData = itemService.getFile(space, folder, fileName);

        byte[] fileBytes = fileData.getBinaryData();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(fileBytes);
    }


}
