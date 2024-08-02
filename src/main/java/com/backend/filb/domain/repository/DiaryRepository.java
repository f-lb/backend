package com.backend.filb.domain.repository;

import com.backend.filb.domain.entity.Diary;
import com.backend.filb.domain.entity.Member;
import com.backend.filb.dto.response.DiaryMonthlyResponse;
import com.backend.filb.dto.response.MonthlyEmotionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary,Long> {

    @Query("SELECT new com.backend.filb.dto.response.DiaryMonthlyResponse(d.diaryId, d.title, d.content, d.createdDate, d.totalEmotion) " +
            "FROM Diary d " +
            "WHERE d.member= :member " +
            "AND MONTH(d.createdDate) = :month")
    List<DiaryMonthlyResponse> findDiariesByMemberAndMonth(Member member, int month);

    @Query("SELECT new com.backend.filb.dto.response.MonthlyEmotionResponse(d.createdDate, r.positiveSentencePercent, r.negativeSentencePercent) " +
            "FROM Diary d " +
            "JOIN d.report r " +
            "WHERE d.createdDate BETWEEN :startDate AND :endDate")
    List<MonthlyEmotionResponse> findReportIdsWithin30Days(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
