package com.backend.filb.domain.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private Integer totalEmotion;
    private String feedback;
    @Embedded
    private Emotions emotions;
    private Integer negativeSentencePercent;
    private Integer positiveSentencePercent;
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
