package com.backend.filb.presentation;

import com.backend.filb.application.DiaryService;
import com.backend.filb.application.JwtService;
import com.backend.filb.dto.request.DiaryRequest;
import com.backend.filb.dto.response.DiaryMonthlyResponse;
import com.backend.filb.dto.response.DiaryResponse;
import com.backend.filb.dto.response.ReportResultResponse;
import com.backend.filb.infra.EmotionApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diaries")
public class DiaryController {
    private DiaryService diaryService;
    private JwtService jwtService;

    public DiaryController(DiaryService diaryService, JwtService jwtService){
        this.diaryService = diaryService;
        this.jwtService = jwtService;
    }

    @PostMapping("/save")
    public ResponseEntity<ReportResultResponse> save(
            @RequestHeader("Authorization") String token,
            @RequestBody DiaryRequest diaryRequest
    ) throws JsonProcessingException {
        String jwtEmail = jwtService.getMemberEmail();
        ReportResultResponse reportResultResponse = diaryService.save(diaryRequest,jwtEmail);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "success");
        return ResponseEntity.ok().headers(headers).body(reportResultResponse);
    }

    @GetMapping("/monthly/{month}")
    public ResponseEntity<List<DiaryMonthlyResponse>> getMonthlyDiaries(
            @RequestHeader("Authorization") String token,
            @PathVariable("month") int month
    ){
        String jwtId = jwtService.getMemberEmail();
        List<DiaryMonthlyResponse> diaryList = diaryService.getMonthlyDiaries(jwtId, month);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "success");
        return ResponseEntity.ok().headers(headers).body(diaryList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaryResponse> getById(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long id
    ){
        String jwtId = jwtService.getMemberEmail();
        DiaryResponse diaryResponse = diaryService.readById(jwtId, id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "success");
        return ResponseEntity.ok().headers(headers).body(diaryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long id
    ){
        String jwtId = jwtService.getMemberEmail();
        diaryService.delete(jwtId,id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "success");
        return ResponseEntity.ok().headers(headers).body(null);
    }
}
