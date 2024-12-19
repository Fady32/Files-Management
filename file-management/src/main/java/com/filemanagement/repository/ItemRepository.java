package com.filemanagement.repository;

import com.filemanagement.model.Item;
import com.filemanagement.model.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByNameAndPermissionGroup(String name, PermissionGroup permissionGroup);

    Optional<Item> findByName(String name);

    Optional<Item> findByNameAndParent_Name(String name, String parentName);

    Optional<Item> findByNameAndParent_NameAndParent_Parent_Name(String fileName, String folderName, String spaceName);
}