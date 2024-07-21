package com.backend.filb.application;

import com.backend.filb.domain.entity.Diary;
import com.backend.filb.domain.entity.Member;
import com.backend.filb.domain.repository.DiaryRepository;
import com.backend.filb.domain.repository.MemberRepository;
import com.backend.filb.dto.DiaryRequest;
import com.backend.filb.dto.DiaryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class DiaryService {
    private DiaryRepository diaryRepository;
    private MemberService memberService;
    private MemberRepository memberRepository;

    public DiaryService(DiaryRepository diaryRepository, MemberService memberService,MemberRepository memberRepository){
        this.diaryRepository = diaryRepository;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    public Diary save(DiaryRequest diaryRequest,String jwtId) {
        Diary diary = MapDiaryRequestToDiary(diaryRequest);
        diary = diaryRepository.save(diary);
        Member member = memberService.findByEmail(jwtId);
        List<Diary> diaryList = member.getDiaryList();
        diaryList.add(diary);
        member.setDiaryList(diaryList);
        memberRepository.save(member);
        return diary;
    }

    public List<DiaryResponse> readAll(String jwtId) {
        Member member = memberService.findByEmail(jwtId);
        List<Diary> diaryList = member.getDiaryList();
        return diaryList.stream()
                .map(this::MapDiaryToDiaryResponse)
                .collect(Collectors.toList());
    }

    public DiaryResponse readById(String jwtId,Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("일기 정보가 없습니다."));
        Member member = memberService.findByEmail(jwtId);
        List<Diary> diaryList = member.getDiaryList();
        if (diaryList.contains(diary)) {
            return MapDiaryToDiaryResponse(diary);
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

    public Diary MapDiaryRequestToDiary(DiaryRequest diaryRequest){
        return new Diary(diaryRequest.diaryId(),diaryRequest.content(),diaryRequest.date());
    }

    public DiaryResponse MapDiaryToDiaryResponse(Diary diary){
        return new DiaryResponse(diary.getDiaryId(),diary.getCreatedDate(),diary.getContent());
    }

}
