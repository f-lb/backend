package com.backend.filb.presentation;

import com.backend.filb.application.ReportService;
import com.backend.filb.dto.response.ReportResponse;
import com.backend.filb.dto.response.ReportResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResultResponse> getById(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok().body(reportService.getByDiaryId(id));
    }
}
