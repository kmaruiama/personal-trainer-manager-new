package com.trainingmanagernew.BodyModule.Dto.Shared;

import java.util.UUID;

public class DeleteResourceDto {
    private String type;
    private UUID id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
