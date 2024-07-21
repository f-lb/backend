package com.backend.filb.application;

import com.backend.filb.domain.entity.Report;
import com.backend.filb.domain.repository.ReportRepository;
import com.backend.filb.dto.ReportResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private ReportRepository reportRepository;

    public List<ReportResponse> findAll() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(this::mapRepositoryToResponseRepository)
                .collect(Collectors.toList());
    }

    public ReportResponse findByReportId(Long id){
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 리포트 정보가 없습니다."));
        return mapRepositoryToResponseRepository(report);
    }

    public ReportResponse save(Report report) {
        return mapRepositoryToResponseRepository(reportRepository.save(report));
    }

    private ReportResponse mapRepositoryToResponseRepository(Report report) {
        return new ReportResponse(
                report.getReportId(),
                report.getTotalEmotion(),
                report.getFeedback(),
                report.getEmotions(),
                report.getNegativeSentencePercent(),
                report.getPositiveSentencePercent(),
                report.getTotalSentenceCount());

    }
}
