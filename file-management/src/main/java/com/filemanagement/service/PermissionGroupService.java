package com.filemanagement.service;

import com.filemanagement.dto.PermissionGroupDTO;
import com.filemanagement.mapper.PermissionGroupMapper;
import com.filemanagement.model.PermissionGroup;
import com.filemanagement.repository.PermissionGroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PermissionGroupService {

    private final PermissionGroupRepository permissionGroupRepository;


    public PermissionGroup getPermissionGroup(String permissionGroupName) {
        return permissionGroupRepository.findByGroupName(permissionGroupName).orElseThrow(() ->
                new EntityNotFoundException("Invalid PermissionGroup name " + permissionGroupName));
    }

    public PermissionGroupDTO createPermissionGroup(PermissionGroupDTO permissionGroupDTO) {
        PermissionGroup entity = PermissionGroupMapper.fromDtoToEntity(permissionGroupDTO);
        entity = permissionGroupRepository.save(entity);
        return PermissionGroupMapper.fromEntityToDto(entity);
    }

    public List<PermissionGroupDTO> getAllPermissionGroups() {
        return permissionGroupRepository.findAll().stream().map(PermissionGroupMapper::fromEntityToDto).collect(Collectors.toList());
    }

    public PermissionGroupDTO getPermissionGroupById(Long id) {
        PermissionGroup permissionGroup = permissionGroupRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Invalid PermissionGroup id " + id));
        return PermissionGroupMapper.fromEntityToDto(permissionGroup);
    }

    public PermissionGroupDTO updatePermissionGroup(Long id, PermissionGroupDTO permissionGroupDTO) {
        if (permissionGroupRepository.existsById(id)) {
            PermissionGroup entity = PermissionGroupMapper.fromDtoToEntity(permissionGroupDTO);
            entity = permissionGroupRepository.save(entity);
            return PermissionGroupMapper.fromEntityToDto(entity);
        }
        return null;
    }


    public void deletePermissionGroup(Long id) {
        permissionGroupRepository.deleteById(id);
    }

}
