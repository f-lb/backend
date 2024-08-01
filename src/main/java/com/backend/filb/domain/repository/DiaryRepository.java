package com.backend.filb.domain.repository;

import com.backend.filb.domain.entity.Diary;
import com.backend.filb.domain.entity.Member;
import com.backend.filb.dto.response.DiaryMonthlyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary,Long> {

    @Query("SELECT new com.backend.filb.dto.response.DiaryMonthlyResponse(d.diaryId, d.title, d.content, d.createdDate, d.totalEmotion) " +
            "FROM Diary d " +
            "WHERE d.member= :member " +
            "AND MONTH(d.createdDate) = :month")
    List<DiaryMonthlyResponse> findDiariesByMemberAndMonth(Member member, int month);
}
