package com.backend.filb.domain.entity;

import jakarta.persistence.*;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(nullable = false)
    private Integer totalEmotion;

    @Column(nullable = false)
    private String feedback;
    @Embedded
    @Column(nullable = false)
    private Emotions emotions;

    @Column(nullable = false)
    private Integer negativeSentencePercent;

    @Column(nullable = false)
    private Integer positiveSentencePercent;

    @Column(nullable = false)
    private Integer totalSentenceCount;

    public Report() {

    }

    public Long getReportId() {
        return reportId;
    }

    public Integer getTotalEmotion() {
        return totalEmotion;
    }

    public String getFeedback() {
        return feedback;
    }

    public Emotions getEmotions() {
        return emotions;
    }

    public Integer getNegativeSentencePercent() {
        return negativeSentencePercent;
    }

    public Integer getPositiveSentencePercent() {
        return positiveSentencePercent;
    }

    public Integer getTotalSentenceCount() {
        return totalSentenceCount;
    }

    public Report(Long reportId, Integer totalEmotion, String feedback, Emotions emotions, Integer negativeSentencePercent, Integer positiveSentencePercent, Integer totalSentenceCount) {
        this.reportId = reportId;
        this.totalEmotion = totalEmotion;
        this.feedback = feedback;
        this.emotions = emotions;
        this.negativeSentencePercent = negativeSentencePercent;
        this.positiveSentencePercent = positiveSentencePercent;
        this.totalSentenceCount = totalSentenceCount;
    }
}
