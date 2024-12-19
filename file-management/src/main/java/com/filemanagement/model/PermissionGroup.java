package com.filemanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "permission_groups")
@Getter
@Setter
@NoArgsConstructor
public class PermissionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String groupName;

    @OneToMany(mappedBy = "permissionGroup")
    private List<Item> items;

    @OneToMany(mappedBy = "permissionGroup")
    private List<Permission> permissions;
}