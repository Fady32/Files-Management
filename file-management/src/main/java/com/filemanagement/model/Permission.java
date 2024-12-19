package com.filemanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permissions", uniqueConstraints = @UniqueConstraint(columnNames = {"user_email", "permission_level"}))
@Getter
@Setter
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PermissionLevel permissionLevel = PermissionLevel.VIEW;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private PermissionGroup permissionGroup;

}