package com.tft.potato.util.file.entity;

import com.tft.potato.common.entity.BaseTimeStampEntity;

import javax.persistence.*;

@Entity(name = "file_entity")
public class FileEntity extends BaseTimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seq")
    private int seq;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size", nullable = false)
    private long size;

//    @Column(name = "upload_ts", nullable = false)
//    private String uploadTs;
//    @Column(name = "update_ts", nullable = false)
//    private String updateTs;

    public FileEntity(String name, int seq, String path, String contentType, long size){
        this.name = name;
        this.seq = seq;
        this.path = path;
        this.contentType = contentType;
        this.size = size;
    }

    @PrePersist
    private void setSeq(){

    }

    @Override
    public String toString(){
        return "File{" +
                "id = " + id + '\'' +
                "seq = " + seq + '\'' +
                "name = " + name + '\'' +
                "path = " + path + '\'' +
                "contentType = " + '\'' +
                "size = " + size + '\'' +
                "uploadTs = " + getCreatedTs() + '\'' +
                "updateTs = " + getModifiedTs() + '\'' +
                '}';
    }

    public void changeFileName(String newFileName){
        this.name = newFileName;
    }

    public void moveFilePath(String newFilePath){
        this.path = newFilePath;
    }



}



