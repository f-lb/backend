package com.backend.filb.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Emotions {
    private Integer happiness;
    private Integer discomfort;
    private Integer fear;
    private Integer surprise;
    private Integer anger;
    private Integer sadness;
}
