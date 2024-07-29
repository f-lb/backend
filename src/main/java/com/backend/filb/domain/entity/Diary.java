package com.backend.filb.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long diaryId;

    @Column(nullable = false)
    LocalDateTime createdDate;

    @Column(nullable = false,length = 1000)
    String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    Report report;

    public Diary(LocalDateTime createdDate, String content) {
        this.createdDate = createdDate;
        this.content = content;
    }

    public Diary() {

    }

    public void setReport(Report report) {
        this.report = report;
    }

}
