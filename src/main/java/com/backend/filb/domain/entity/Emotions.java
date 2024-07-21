package com.backend.filb.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Emotions {
    @Column(nullable = false)
    private Integer happiness;
    @Column(nullable = false)
    private Integer discomfort;
    @Column(nullable = false)
    private Integer fear;
    @Column(nullable = false)
    private Integer surprise;
    @Column(nullable = false)
    private Integer anger;
    @Column(nullable = false)
    private Integer sadness;
}
