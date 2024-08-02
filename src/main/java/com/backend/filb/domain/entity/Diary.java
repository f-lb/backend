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
    private String title;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "report_id")
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    private Integer totalEmotion;

    public Diary(String title, LocalDateTime createdDate, String content, Member member) {
        this.title = title;
        this.createdDate = createdDate;
        this.content = content;
        this.member = member;
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
