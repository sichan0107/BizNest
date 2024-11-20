package com.tft.potato.common.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeStampEntity {

    @Column(name = "created_ts", nullable = false, length = 14)
    private Timestamp createdTs;

    //@Column(name = "created_user_id", updatable = false, nullable = false, length = 11)
    //private String createdUserId;

    @Column(name = "modified_ts", nullable = false, length = 14)
    private Timestamp modifiedTs;

    //@Column(name = "modified_user_id", updatable = false, nullable = false, length = 11)
    //private String modifiedUserId;


    @PrePersist
    public void prePersist() {
        setFirstData();
        setLastData();
    }

    @PreUpdate
    public void preUpdate() {
        setLastData();
    }

    private void setFirstData() {
        createdTs = Timestamp.valueOf(LocalDateTime.now());
        //todo 추후 로그인되면 해당코드 추가 필요 (유저 ID 넣는거 필요)
        //createdUserId = "";
    }

    private void setLastData() {
        modifiedTs = Timestamp.valueOf(LocalDateTime.now());
        //todo 추후 로그인되면 해당코드 추가 필요
        //modifiedUserId = "";
    }
}
