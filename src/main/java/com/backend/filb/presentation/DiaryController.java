package com.backend.filb.presentation;

import com.backend.filb.application.DiaryService;
import com.backend.filb.application.JwtService;
import com.backend.filb.dto.DiaryRequest;
import com.backend.filb.dto.DiaryResponse;
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
    public ResponseEntity<DiaryResponse> save(
            @RequestHeader("Authorization") String token,
            @RequestBody DiaryRequest diaryRequest
    ) throws JsonProcessingException {
        String jwtEmail = jwtService.getMemberEmail();
        DiaryResponse diaryResponse = diaryService.save(diaryRequest,jwtEmail);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "success");
        return ResponseEntity.ok().headers(headers).body(diaryResponse);
    }

    @PostMapping("/read")
    public ResponseEntity<List<DiaryResponse>> readAll(
            @RequestHeader("Authorization") String token
    ){
        String jwtId = jwtService.getMemberEmail();
        List<DiaryResponse> diaryList = diaryService.readAll(jwtId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "success");
        return ResponseEntity.ok().headers(headers).body(diaryList);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<DiaryResponse> readById(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long id
    ){
        String jwtId = jwtService.getMemberEmail();
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
        String jwtId = jwtService.getMemberEmail();
        diaryService.delete(jwtId,id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "success");
        return ResponseEntity.ok().headers(headers).body(null);
    }
}
