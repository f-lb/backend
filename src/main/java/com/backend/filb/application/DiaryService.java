package com.backend.filb.application;

import com.backend.filb.domain.entity.Diary;
import com.backend.filb.domain.entity.Member;
import com.backend.filb.domain.entity.Report;
import com.backend.filb.domain.repository.DiaryRepository;
import com.backend.filb.domain.repository.MemberRepository;
import com.backend.filb.domain.repository.ReportRepository;
import com.backend.filb.dto.request.DiaryRequest;
import com.backend.filb.dto.response.DiaryMonthlyResponse;
import com.backend.filb.dto.response.DiaryResponse;
import com.backend.filb.dto.response.MonthlyEmotionResponse;
import com.backend.filb.dto.response.ReportResultResponse;
import com.backend.filb.infra.EmotionApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DiaryService {
    private DiaryRepository diaryRepository;
    private MemberService memberService;
    private MemberRepository memberRepository;
    private ReportRepository reportRepository;
    private EmotionApi emotionApi;

    public DiaryService(DiaryRepository diaryRepository, MemberService memberService, MemberRepository memberRepository, ReportRepository reportRepository, EmotionApi emotionApi) {
        this.diaryRepository = diaryRepository;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.reportRepository = reportRepository;
        this.emotionApi = emotionApi;
    }

    @Transactional
    public ReportResultResponse save(DiaryRequest diaryRequest, String jwtId) throws JsonProcessingException {
        Member member = memberService.findByEmail(jwtId);
        Diary diary = mapDiaryRequestToDiary(diaryRequest, member);
        ResponseEntity<Object> emotionResponse = emotionApi.getEmotionResponse(diaryRequest.content());
        Report report = Report.of(emotionResponse, diary);
        report.setFeedback(getFeedBack(diaryRequest.content()));
        reportRepository.save(report);
        diary.setReport(report);
        diary = diaryRepository.save(diary);
        member.setDiary(diary);
        memberRepository.save(member);
        List<MonthlyEmotionResponse> monthlyEmotionResponses = getMonthlyEmotion(member, diaryRequest.date());
        return mapToReportResult(diary, report, monthlyEmotionResponses);
    }

    private List<MonthlyEmotionResponse> getMonthlyEmotion(Member member, LocalDateTime endDate) {
        LocalDateTime startDate = endDate.minusDays(30);
        return diaryRepository.findReportIdsWithin30Days(startDate, endDate, member);
    }

    public List<DiaryMonthlyResponse> getMonthlyDiaries(String jwtId, int month) {
        Member member = memberService.findByEmail(jwtId);
        List<DiaryMonthlyResponse> response = diaryRepository.findDiariesByMemberAndMonth(member, month);
        return response.stream()
                .map(DiaryMonthlyResponse::updatePrefix)
                .toList();
    }

    public String getFeedBack(String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<Object> feedBack = emotionApi.getReport(content);
        String responseBody = objectMapper.writeValueAsString(feedBack.getBody());

        JsonNode rootNode = objectMapper.readTree(responseBody);
        String extractedContent = rootNode.path("choices").get(0).path("message").path("content").asText();

        return extractedContent;
    }

    public DiaryResponse readById(String jwtEmail, Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("일기 정보가 없습니다."));
        return new DiaryResponse(diary.getDiaryId(), diary.getCreatedDate(), diary.getTitle(), diary.getContent(), diary.getTotalEmotion());
    }

    public void delete(String jwtId, Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("일기 정보가 없습니다."));
        diaryRepository.delete(diary);
    }

    public Diary mapDiaryRequestToDiary(DiaryRequest diaryRequest, Member member){
        return new Diary(diaryRequest.title(), diaryRequest.date(), diaryRequest.content(), member);
    }

    public ReportResultResponse mapToReportResult(Diary diary, Report report, List<MonthlyEmotionResponse> monthlyEmotionResponses){
        return new ReportResultResponse(diary.getCreatedDate(), report.getEmotions(), report.getTotalEmotion(), report.getTotalEmotionPercent(), report.getFeedback(), report.getTotalSentenceCount(), report.getPositiveSentencePercent(), report.getNegativeSentencePercent(), report.getEmotionSentences(), monthlyEmotionResponses);
    }

}
