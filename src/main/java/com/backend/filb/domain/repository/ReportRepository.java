package com.backend.filb.domain.repository;

import com.backend.filb.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {

    List<Report> findBy
}
