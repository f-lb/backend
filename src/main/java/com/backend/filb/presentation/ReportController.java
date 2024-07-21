package com.backend.filb.presentation;

import com.backend.filb.application.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

}
