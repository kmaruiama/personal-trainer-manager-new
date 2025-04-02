package com.trainingmanagernew.BodyModule.Dto.Height;

import java.time.LocalDate;
import java.util.UUID;

public class HeightGetDto {
    private UUID id;
    private float height;
    private LocalDate date;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
