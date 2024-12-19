package com.filemanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class File {
    String contentType;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long size;
    @Lob
    private byte[] binaryData;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    private String createdBy;

}