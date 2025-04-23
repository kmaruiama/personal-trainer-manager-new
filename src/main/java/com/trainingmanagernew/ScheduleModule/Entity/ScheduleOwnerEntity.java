package com.trainingmanagernew.ScheduleModule.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class ScheduleOwnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;

    @OneToMany
    private List<ScheduleEntity> scheduleEntityList = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<ScheduleEntity> getScheduleEntityList() {
        return scheduleEntityList;
    }

    public void setScheduleEntityList(List<ScheduleEntity> scheduleEntityList) {
        this.scheduleEntityList = scheduleEntityList;
    }
}
