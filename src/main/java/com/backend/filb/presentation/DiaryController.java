package com.backend.filb.presentation;

import com.backend.filb.application.DiaryService;
import com.backend.filb.application.JwtService;
import com.backend.filb.application.MemberService;
import com.backend.filb.domain.entity.Diary;
import com.backend.filb.domain.entity.Member;
import com.backend.filb.domain.entity.Report;
import com.backend.filb.domain.repository.DiaryRepository;
import com.backend.filb.dto.DiaryRequest;
import com.backend.filb.dto.DiaryResponse;
import com.backend.filb.dto.ReportResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diaries")
public class DiaryController {
    private DiaryService diaryService;
    private JwtService jwtService;
    private MemberService memberService;

    public DiaryController(DiaryService diaryService, JwtService jwtService,MemberService memberService){
        this.diaryService = diaryService;
        this.jwtService = jwtService;
        this.memberService = memberService;
    }

    @PostMapping("/save")
    public ResponseEntity<ReportResponse> save(
            @RequestHeader("Authorization") String token,
            @RequestBody DiaryRequest diaryRequest
    ){
        String jwtId = jwtService.getMemberId();
        ReportResponse reportResponse = diaryService.save(diaryRequest,jwtId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "success");
        return ResponseEntity.ok().headers(headers).body(null);
    }

    @PostMapping("/read")
    public ResponseEntity<List<DiaryResponse>> readAll(
            @RequestHeader("Authorization") String token
    ){
        String jwtId = jwtService.getMemberId();
        List<DiaryResponse> diaryList = diaryService.readAll(jwtId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "success");
        return ResponseEntity.ok().headers(headers).body(diaryList);
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<DiaryResponse> readById(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long id
    ){
        String jwtId = jwtService.getMemberId();
        DiaryResponse diaryResponse = diaryService.readById(jwtId,id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "success");
        return ResponseEntity.ok().headers(headers).body(diaryResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long id
    ){
        String jwtId = jwtService.getMemberId();
        diaryService.delete(jwtId,id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "success");
        return ResponseEntity.ok().headers(headers).body(null);
    }
}
