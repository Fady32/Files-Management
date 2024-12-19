package com.filemanagement.repository;

import com.filemanagement.model.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long> {
    Optional<PermissionGroup> findByGroupName(String groupName);

    Optional<PermissionGroup> findByItems_Id(Long itemId);
}