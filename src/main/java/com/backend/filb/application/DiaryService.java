package com.backend.filb.application;

import com.backend.filb.domain.entity.Diary;
import com.backend.filb.domain.entity.Member;
import com.backend.filb.domain.entity.Report;
import com.backend.filb.domain.repository.DiaryRepository;
import com.backend.filb.domain.repository.MemberRepository;
import com.backend.filb.dto.DiaryRequest;
import com.backend.filb.dto.DiaryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DiaryService {
    private DiaryRepository diaryRepository;
    private MemberService memberService;
    private MemberRepository memberRepository;
    private ReportService reportService;

    public DiaryService(DiaryRepository diaryRepository, MemberService memberService,MemberRepository memberRepository,ReportService reportService){
        this.diaryRepository = diaryRepository;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.reportService = reportService;
    }

    public DiaryResponse save(DiaryRequest diaryRequest, String jwtId) throws JsonProcessingException {
        Report report = reportService.makeReport(diaryRequest.content());
        Diary diary = mapDiaryRequestToDiary(diaryRequest);
        diary.setReport(report);
        diary = diaryRepository.save(diary);
        Member member = memberService.findByEmail(jwtId);
        List<Diary> diaryList = member.getDiaryList();
        diaryList.add(diary);
        member.setDiaryList(diaryList);
        memberRepository.save(member);
        return mapDiaryToDiaryResponse(diary);
    }

    public List<DiaryResponse> readAll(String jwtId) {
        Member member = memberService.findByEmail(jwtId);
        List<Diary> diaryList = member.getDiaryList();
        return diaryList.stream()
                .map(this::mapDiaryToDiaryResponse)
                .toList();
    }

    public DiaryResponse readById(String jwtEmail,Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("일기 정보가 없습니다."));
        Member member = memberService.findByEmail(jwtEmail);
        List<Diary> diaryList = member.getDiaryList();
        if (diaryList.contains(diary)) {
            return mapDiaryToDiaryResponse(diary);
        }
        throw new NoSuchElementException("해당 일기에 대한 권한이 없습니다.");
    }

    public void delete(String jwtId, Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("일기 정보가 없습니다."));
        Member member = memberService.findByEmail(jwtId);
        List<Diary> diaryList = member.getDiaryList();
        if (diaryList.contains(diary)) {
            diaryList.remove(diary);
            member.setDiaryList(diaryList);
            memberRepository.save(member);
            diaryRepository.deleteById(id);
            return;
        }
        throw new NoSuchElementException("해당 일기에 대한 권한이 없습니다.");
    }

    public Diary mapDiaryRequestToDiary(DiaryRequest diaryRequest){
        return new Diary(diaryRequest.diaryId(),diaryRequest.content(),diaryRequest.date(),diaryRequest.report());
    }

    public DiaryResponse mapDiaryToDiaryResponse(Diary diary){
        return new DiaryResponse(diary.getDiaryId(),diary.getCreatedDate(),diary.getContent(),diary.getReport());
    }

}
