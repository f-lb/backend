package com.backend.filb.application;

import com.backend.filb.domain.entity.Diary;
import com.backend.filb.domain.entity.Member;
import com.backend.filb.domain.repository.DiaryRepository;
import com.backend.filb.dto.DiaryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryService {
    private DiaryRepository diaryRepository;
    private MemberService memberService;

    public DiaryService(DiaryRepository diaryRepository, MemberService memberService){
        this.diaryRepository = diaryRepository;
        this.memberService = memberService;
    }

    public Diary save(DiaryRequest diaryRequest,String jwtId) {
        Diary diary = MapDiaryRequestToDiary(diaryRequest);
        diary = diaryRepository.save(diary);
        Member member = memberService.findByEmail(jwtId);
        List<Diary> diaryList = member.getDiaryList();
        diaryList.add(diary);
        member.setDiaryList(diaryList);
        return diary;
    }

    public Diary MapDiaryRequestToDiary(DiaryRequest diaryRequest){
        return new Diary(diaryRequest.diaryId(),diaryRequest.content(),diaryRequest.date());
    }
}
