package com.backend.filb.domain.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long diaryId;
    @Column(nullable = false)
    Date createdDate;
    @Column(nullable = false)
    String content;

    public Diary(Long diaryId, String content, Date createdDate) {
        this.diaryId = diaryId;
        this.content = content;
        this.createdDate = createdDate;
    }

    public Diary() {

    }

    public Long getDiaryId() {
        return diaryId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getContent() {
        return content;
    }
}
