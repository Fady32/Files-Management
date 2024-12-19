package com.filemanagement.repository;

import com.filemanagement.dto.FileMetadata;
import com.filemanagement.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileEntityRepository extends JpaRepository<File, Long> {
    Optional<File> findByItem_Id(Long itemId);

    @Query(value = "SELECT new com.filemanagement.dto.FileMetadata(file.id, it.name , file.size , file.createdAt , file.createdBy , file.contentType ) From File file" +
            " JOIN file.item it " +
            "WHERE it.id = :itemId")
    FileMetadata getFileMetadata(@Param("itemId") String itemId);
}