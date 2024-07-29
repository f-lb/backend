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
    @Column(nullable = false,length = 1000)
    String content;

    @OneToOne
    Report report;

    public Diary(Long diaryId, String content, Date createdDate,Report report) {
        this.diaryId = diaryId;
        this.content = content;
        this.createdDate = createdDate;
        this.report = report;
    }

    public Diary() {

    }


    public void setReport(Report report) {
        this.report = report;
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

    public Report getReport() {
        return report;
    }
}
