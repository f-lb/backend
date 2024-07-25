package com.backend.filb.application;

import com.backend.filb.domain.entity.Emotions;
import com.backend.filb.domain.entity.Report;
import com.backend.filb.domain.repository.ReportRepository;
import com.backend.filb.dto.DiaryRequestToAi;
import com.backend.filb.dto.ReportResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

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

    public Report makeReport(String content) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        DiaryRequestToAi diaryRequestToAi = new DiaryRequestToAi(content);
        var url = "http://localhost:8000/predict";

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(diaryRequestToAi);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(jsonBody,headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                Object.class);

        Report report = getStatics(responseEntity);
        reportRepository.save(report);
        return report;

    }

    private Report getStatics(ResponseEntity<Object> responseEntity) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(responseEntity.getBody());

        Map<String, Object> map = mapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {});

        Map<String, Integer> predictions = (Map<String, Integer>) map.get("predictions");

        int[] emotions = new int[5];

        for (Integer value : predictions.values()) {
            if (value >= 0 && value < emotions.length) {
                emotions[value]++;
            }
        }

        int sum = Arrays.stream(emotions).sum();
        int[] persentOfEmotions = new int[5];

        for (int i = 0;i<5;i++) {
            persentOfEmotions[i] = (int)(((double)emotions[i] / sum) * 100);
        }

        //totalEmotion 구하는 법은 상의 필요
        int totalEmotion = 0;

        //feedBack은 연동해야함
        String feedBack = "";

        Emotions emotionsResult = new Emotions(persentOfEmotions[0],persentOfEmotions[1]
                ,persentOfEmotions[2],persentOfEmotions[3],persentOfEmotions[4]);

        int positiveSentencePercent = persentOfEmotions[4];
        int negativeSentencePercent = 100 - persentOfEmotions[4];

        int totalSentenceCount = sum;

        return new Report(null,totalEmotion,feedBack,emotionsResult,negativeSentencePercent,positiveSentencePercent,totalSentenceCount);
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
