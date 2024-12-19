package com.filemanagement.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "type" , "parent_id"})
})
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private PermissionGroup permissionGroup;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Item parent;
}

