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
import com.backend.filb.dto.response.ReportResultResponse;
import com.backend.filb.infra.EmotionApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        Diary diary = mapDiaryRequestToDiary(diaryRequest);
        ResponseEntity<Object> emotionResponse = emotionApi.getEmotionResponse(diaryRequest.content());
        Report report = Report.of(emotionResponse, diary);
        reportRepository.save(report);
        diary.setReport(report);
        diary = diaryRepository.save(diary);
        Member member = memberService.findByEmail(jwtId);
        member.setDiary(diary);
        memberRepository.save(member);
        return mapToReportResult(diary, report);
    }

    public List<DiaryMonthlyResponse> getMonthlyDiaries(String jwtId, Integer month) {
        Member member = memberService.findByEmail(jwtId);
        return diaryRepository.findDiariesByMemberAndMonth(member.getEmail(), month);
    }

    public DiaryResponse readById(String jwtEmail, Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("일기 정보가 없습니다."));
        return new DiaryResponse(diary.getDiaryId(), diary.getCreatedDate(), diary.getContent());
    }

    public void delete(String jwtId, Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("일기 정보가 없습니다."));
        diaryRepository.delete(diary);
    }

    public Diary mapDiaryRequestToDiary(DiaryRequest diaryRequest){
        return new Diary(diaryRequest.date(), diaryRequest.content());
    }

    public ReportResultResponse mapToReportResult(Diary diary, Report report){
        return new ReportResultResponse(diary.getCreatedDate(), report.getEmotions(), report.getTotalEmotion(), report.getFeedback(), report.getTotalSentenceCount(), report.getPositiveSentencePercent(), report.getNegativeSentencePercent());
    }

}
