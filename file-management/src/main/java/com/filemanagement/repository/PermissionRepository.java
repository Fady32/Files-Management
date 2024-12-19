package com.filemanagement.repository;

import com.filemanagement.dto.UserAuthorityDto;
import com.filemanagement.model.Permission;
import com.filemanagement.model.PermissionGroup;
import com.filemanagement.model.PermissionLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByUserEmailAndPermissionGroup(String userEmail, PermissionGroup permissionGroup);

    @Query("SELECT new com.filemanagement.dto.UserAuthorityDto(p.permissionLevel, pg.groupName) " +
            "FROM Permission p JOIN p.permissionGroup pg " +
            "WHERE p.userEmail = :userEmail")
    List<UserAuthorityDto> findPermissionsByUserEmail(@Param("userEmail") String userEmail);

    boolean existsPermissionByUserEmail(String email);

    List<Permission> findByUserEmailAndPermissionLevel(String userEmail, PermissionLevel permissionLevel);
}