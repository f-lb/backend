package com.backend.filb.application;

import com.backend.filb.domain.entity.Diary;
import com.backend.filb.domain.entity.Member;
import com.backend.filb.domain.entity.Report;
import com.backend.filb.domain.repository.DiaryRepository;
import com.backend.filb.dto.response.MonthlyEmotionResponse;
import com.backend.filb.dto.response.ReportResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final DiaryRepository diaryRepository;

    public ReportResultResponse getByDiaryId(Long id) {
        Diary diary = diaryRepository.findById(id).orElseThrow(() -> new RuntimeException("해당되는 id의 일기장을 찾을 수 없습니다."));
        Report report = diary.getReport();
        List<MonthlyEmotionResponse> monthlyEmotionResponses = getMonthlyEmotion(diary.getCreatedDate(), diary.getMember());
        return Report.toDto(diary, report, monthlyEmotionResponses);
    }

    private List<MonthlyEmotionResponse> getMonthlyEmotion(LocalDateTime endDate, Member member) {
        LocalDateTime startDate = endDate.minusDays(30);
        return diaryRepository.findReportIdsWithin30Days(startDate, endDate, member);
    }
}

