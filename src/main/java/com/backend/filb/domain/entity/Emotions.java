package com.backend.filb.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Emotions {
    @Column(nullable = false)
    private Integer happiness;
    @Column(nullable = false)
    private Integer embarrassment;
    @Column(nullable = false)
    private Integer anxiety;
    @Column(nullable = false)
    private Integer anger;
    @Column(nullable = false)
    private Integer sadness;

    public Emotions(Integer anxiety,Integer embarrassment,Integer anger,Integer sadness,Integer happiness) {
        this.happiness = happiness;
        this.embarrassment = embarrassment;
        this.anxiety = anxiety;
        this.anger = anger;
        this.sadness = sadness;
    }

    public Emotions() {

    }
}
