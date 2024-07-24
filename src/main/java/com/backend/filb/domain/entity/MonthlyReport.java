package com.backend.filb.domain.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class MonthlyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long monthlyReportId;

    private Long averageResponseTime;

    private Long overallHappinessScore;

    private Long totalHappiness;

    private Long totalFear;

    private Long totalAnger;

    private Long totalSadness;

    @Temporal(TemporalType.DATE)
    private Date month;

    @ManyToOne
    private Member member;
}
