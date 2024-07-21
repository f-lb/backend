package com.backend.filb.presentation;

import com.backend.filb.application.ReportService;
import com.backend.filb.dto.ReportResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<ReportResponse>> readAll(
    ) {
        return ResponseEntity.ok().body(reportService.findall());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> readById(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok().body(reportService.findByReportId(id));
    }


}
