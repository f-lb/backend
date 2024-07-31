package com.backend.filb.application;

import com.backend.filb.domain.entity.Diary;
import com.backend.filb.domain.entity.Emotions;
import com.backend.filb.domain.entity.Report;
import com.backend.filb.domain.repository.DiaryRepository;
import com.backend.filb.domain.repository.ReportRepository;
import com.backend.filb.dto.request.DiaryRequestToAi;
import com.backend.filb.dto.response.ReportResponse;
import com.backend.filb.dto.response.ReportResultResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final DiaryRepository diaryRepository;

    public ReportResultResponse getByDiaryId(Long id) {
        Diary diary = diaryRepository.findById(id).orElseThrow(() -> new RuntimeException("해당되는 id의 일기장을 찾을 수 없습니다."));
        Report report = diary.getReport();
        return Report.toDto(diary, report);
    }
}
