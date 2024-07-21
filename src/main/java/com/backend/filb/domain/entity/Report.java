package com.backend.filb.domain.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private Date date;
    private String diary;
    private Integer totalEmotion;
    private String Feedback;
    @Embedded
    private Emotions emotions;
    private Integer negativeSentencePercent;
    private Integer positiveSentencePercent;
    private Integer totalSentenceCount;
}
