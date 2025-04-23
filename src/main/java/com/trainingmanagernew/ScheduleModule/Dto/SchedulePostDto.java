package com.trainingmanagernew.ScheduleModule.Dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

public class SchedulePostDto {

    private UUID optionalScheduleId;

    @NotNull(message = "O dia da semana não pode ser nulo.")
    private int dayOfTheWeek;

    @NotNull(message = "O horário de início não pode ser nula.")
    private LocalTime hourStart;

    @NotNull(message = "O horário de fim não pode ser nulo.")
    private LocalTime hourEnd;

    @NotNull(message = "O id do owner não pode ser nulo.")
    private UUID scheduleOwnerId;

    public UUID getOptionalScheduleId() {
        return optionalScheduleId;
    }

    public void setOptionalScheduleId(UUID optionalScheduleId) {
        this.optionalScheduleId = optionalScheduleId;
    }

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

    public UUID getScheduleOwnerId() {
        return scheduleOwnerId;
    }

    public void setScheduleOwnerId(UUID scheduleOwnerId) {
        this.scheduleOwnerId = scheduleOwnerId;
    }
}
