package com.trainingmanagernew.ScheduleModule.Entity;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.UUID;

@Entity
public class ScheduleEntity {
    //adicionar o id do treino, do cliente e ambos os nomes aqui tb
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private int dayOfTheWeek;

    private LocalTime hourStart;

    private LocalTime hourEnd;

    @ManyToOne
    private ScheduleOwnerEntity scheduleOwnerEntity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public ScheduleOwnerEntity getScheduleOwner() {
        return scheduleOwnerEntity;
    }

    public void setScheduleOwner(ScheduleOwnerEntity scheduleOwnerEntity) {
        this.scheduleOwnerEntity = scheduleOwnerEntity;
    }
}
