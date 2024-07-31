package com.backend.filb.domain.repository;

import com.backend.filb.domain.entity.Diary;
import com.backend.filb.dto.response.DiaryMonthlyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary,Long> {

    @Query("SELECT new com.backend.filb.dto.response.DiaryMonthlyResponse(d.createdDate, d.totalEmotion) " +
            "FROM Diary d " +
            "WHERE d.member.email = :email " +
            "AND MONTH(d.createdDate) = :month")
    List<DiaryMonthlyResponse> findDiariesByMemberAndMonth(@Param("email") String email, @Param("month") Integer month);
}
