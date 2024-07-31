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
    private Long diaryId;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false,length = 1000)
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;
    private Integer totalEmotion;

    public Diary(LocalDateTime createdDate, String content) {
        this.createdDate = createdDate;
        this.content = content;
    }

    public Diary() {

    }

    public void setReport(Report report) {
        this.report = report;
    }

    public void updateTotalEmotion(int totalEmotionIndex) {
        this.totalEmotion = totalEmotionIndex;
    }
}
