package com.filemanagement.service;

import com.filemanagement.dto.FileMetadata;
import com.filemanagement.dto.ItemDto;
import com.filemanagement.mapper.ItemMapper;
import com.filemanagement.model.*;
import com.filemanagement.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    private final PermissionGroupService permissionGroupService;

    private final PermissionService permissionService;

    private final FileService fileService;

    /**
     * Creates a new item and handles item-specific logic based on its type.
     *
     * @param folderName          The name of the folder or space.
     * @param type                The type of the item (SPACE, FOLDER, or FILE).
     * @param permissionGroupName The name of the permission group.
     * @param spaceName           The space to which the item belongs.
     * @param file                The file to be uploaded (if type is FILE).
     * @return ItemDto            The DTO of the created item.
     */
    @Transactional
    public ItemDto createItem(String folderName, ItemType type, String permissionGroupName, String spaceName, MultipartFile file) {
        Item item = new Item();
        item.setName(folderName);
        item.setType(type);

        switch (type) {
            case SPACE -> item = handleSpaceItem(item, permissionGroupName);
            case FOLDER -> item = handleFolderItem(item, spaceName);
            case FILE -> item = handleFileItem(item, folderName, spaceName, file);
            default -> throw new UnsupportedOperationException("Unsupported item type: " + type);
        }


        return ItemMapper.toDto(item);
    }

    /**
     * Handle creation of a SPACE item.
     *
     * @param item                The item to be created.
     * @param permissionGroupName The permission group name for the space.
     */
    private Item handleSpaceItem(Item item, String permissionGroupName) {
        PermissionGroup group = permissionGroupService.getPermissionGroup(permissionGroupName);
        item.setPermissionGroup(group);
        return itemRepository.save(item);
    }

    /**
     * Handle creation of a FOLDER item within an existing space.
     *
     * @param item      The item to be created.
     * @param spaceName The name of the space in which the folder will be created.
     */
    private Item handleFolderItem(Item item, String spaceName) {
        Item space = findByName(spaceName);

        permissionService.checkPermissions(space.getPermissionGroup().getGroupName(), PermissionLevel.EDIT);

        item.setPermissionGroup(space.getPermissionGroup());
        item.setParent(space);
        return itemRepository.save(item);
    }

    /**
     * Handle creation of a FILE item and upload the file.
     *
     * @param item       The item to be created.
     * @param folderName The name of the folder (optional).
     * @param spaceName  The name of the space.
     * @param file       The file to be uploaded.
     * @return Item      The Entity of the created file item.
     */
    private Item handleFileItem(Item item, String folderName, String spaceName, MultipartFile file) {
        Item parent = (folderName != null) ?
                findByNameAndParentName(spaceName, folderName) : findByName(spaceName);

        String userEmail = permissionService.checkPermissions(parent.getPermissionGroup().getGroupName(), PermissionLevel.EDIT);


        item.setName(file.getOriginalFilename());
        item.setPermissionGroup(parent.getPermissionGroup());
        item.setParent(parent);

        try {
            // Save item and upload file
            item = itemRepository.save(item);
            fileService.uploadFile(item, file, userEmail);
            return item;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    /**
     * Finds an item by its name.
     *
     * @param spaceName The name of the space.
     * @return Item     The found item.
     * @throws EntityNotFoundException If the item is not found.
     */
    private Item findByName(String spaceName) {
        return itemRepository.findByName(spaceName)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + spaceName));
    }

    /**
     * Finds a folder by its name and the name of its parent (space).
     *
     * @param spaceName  The name of the space.
     * @param folderName The name of the folder.
     * @return Item      The found folder item.
     * @throws EntityNotFoundException If the folder is not found.
     */
    private Item findByNameAndParentName(String spaceName, String folderName) {
        return itemRepository.findByNameAndParent_Name(folderName, spaceName)
                .orElseThrow(() -> new EntityNotFoundException("Folder not found: " + folderName));
    }


    public FileMetadata getFileMetadata(String space, String folder, String fileName) throws Exception {
        // Check permissions before viewing file metadata
        Item fileItem = getFileItem(space, folder, fileName);

        return fileService.getFileMetadata(String.valueOf(fileItem.getId()));
    }

    @Transactional
    public File getFile(String space, String folder, String fileName) throws Exception {
        Item fileItem = getFileItem(space, folder, fileName);

        return fileService.downloadFile(fileItem.getId());
    }

    private Item getFileItem(String space, String folder, String fileName) throws Exception {
        // Check permissions before viewing file metadata
        Optional<Item> fileItem;
        if (folder != null) {
            fileItem = itemRepository.findByNameAndParent_NameAndParent_Parent_Name(fileName, folder, space);
        } else {
            fileItem = itemRepository.findByNameAndParent_Name(fileName, space);
        }

        if (fileItem.isEmpty()) {
            throw new AccessDeniedException("No permissions to read item: " + fileName);
        }

        permissionService.checkPermissions(fileItem.get().getPermissionGroup().getGroupName(), PermissionLevel.VIEW);
        return fileItem.get();
    }
}
