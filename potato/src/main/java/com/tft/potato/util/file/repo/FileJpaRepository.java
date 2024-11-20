package com.tft.potato.util.file.repo;

import com.tft.potato.util.file.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileJpaRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByName(String fileName);
    Optional<FileEntity> findById(String id);

    Optional<List<FileEntity>> findAllByPath(String path);



}
