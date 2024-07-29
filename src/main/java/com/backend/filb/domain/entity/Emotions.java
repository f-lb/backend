package com.backend.filb.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Emotions {
    @Column(nullable = false)
    private Integer happiness;
    @Column(nullable = false)
    private Integer surprised;
    @Column(nullable = false)
    private Integer anxiety;
    @Column(nullable = false)
    private Integer anger;
    @Column(nullable = false)
    private Integer sadness;
    @Column(nullable = false)
    private Integer neutrality;

    public Emotions(Integer happiness,Integer surprised,Integer anger,Integer anxiety,Integer sadness,Integer neutrality) {
        this.happiness = happiness;
        this.surprised = surprised;
        this.anxiety = anxiety;
        this.anger = anger;
        this.sadness = sadness;
        this.neutrality = neutrality;
    }

    public Emotions() {
    }

    public Integer getHappiness(){
        return happiness;
    }

    public Integer getSurprised() {
        return surprised;
    }

    public Integer getAnxiety() {
        return anxiety;
    }

    public Integer getAnger() {
        return anger;
    }

    public Integer getSadness() {
        return sadness;
    }

    public Integer getNeutrality() {
        return neutrality;
    }
}
