package com.filemanagement.service;

import com.filemanagement.dto.PermissionDTO;
import com.filemanagement.dto.UserAuthorityDto;
import com.filemanagement.mapper.PermissionMapper;
import com.filemanagement.model.Permission;
import com.filemanagement.model.PermissionGroup;
import com.filemanagement.model.PermissionLevel;
import com.filemanagement.repository.PermissionGroupRepository;
import com.filemanagement.repository.PermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionGroupRepository permissionGroupRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository, PermissionGroupRepository permissionGroupRepository) {
        this.permissionRepository = permissionRepository;
        this.permissionGroupRepository = permissionGroupRepository;
    }

    /**
     * Check if a user has the required permission level on the given item.
     */
    public String checkPermissions(String requiredGroup, PermissionLevel requiredLevel) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getAuthorities().isEmpty()) {
            boolean authorized = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority ->
                            grantedAuthority.getAuthority().equals(requiredGroup.concat("_").concat(requiredLevel.name())));

            if (!authorized) {
                throw new AccessDeniedException("You do not have permission to access this resource.");
            }

            return authentication.getName();
        }
        throw new AccessDeniedException("You do not have permission to access this resource.");

    }

    public List<String> getPermissionsGroupPerUser(String userEmail) {
        List<UserAuthorityDto> permissions = getUserAuthorities(userEmail);

        return permissions.stream()
                .map(permission -> permission.getGroupName() + "_" + permission.getPermissionName().name())
                .collect(Collectors.toList()); // Collect into a Set
    }

    private List<UserAuthorityDto> getUserAuthorities(String userEmail) {
        return permissionRepository.findPermissionsByUserEmail(userEmail);
    }

    public PermissionDTO createPermission(PermissionDTO permissionDTO) {

        PermissionGroup permissionGroup = permissionGroupRepository.findByGroupName(permissionDTO.getPermissionGroupName())
                .orElseThrow(() -> new EntityNotFoundException("Permission group not found."));

        Permission permission = PermissionMapper.fromDtoToEntity(permissionDTO);
        permission.setPermissionGroup(permissionGroup);
        permission = permissionRepository.save(permission);
        return PermissionMapper.fromEntityToDto(permission);
    }


    public void deletePermission(String userEmail, PermissionLevel level) {
        List<Permission> byUserEmailAndPermissionLevel = permissionRepository.findByUserEmailAndPermissionLevel(userEmail, level);
        permissionRepository.deleteAll(byUserEmailAndPermissionLevel);
    }
}
