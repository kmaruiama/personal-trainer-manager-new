package com.trainingmanagernew.ScheduleModule.Dto;

import java.time.LocalTime;
import java.util.UUID;

public class ValidateScheduleDto {
    private int dayOfTheWeek;
    private LocalTime hourStart;
    private LocalTime hourEnd;
    private UUID ownerId;
    public int getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(int dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public LocalTime getHourStart() {
        return hourStart;
    }

    public void setHourStart(LocalTime hourStart) {
        this.hourStart = hourStart;
    }

    public LocalTime getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(LocalTime hourEnd) {
        this.hourEnd = hourEnd;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }
}
