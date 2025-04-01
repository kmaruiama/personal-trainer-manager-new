package com.trainingmanagernew.BodyModule.Dto;

import java.time.LocalDate;
import java.util.UUID;

public class BodyGetDto {
    private UUID id;
    private LocalDate date;
    private float weight;
    private float bodyFat;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(float bodyFat) {
        this.bodyFat = bodyFat;
    }
}
