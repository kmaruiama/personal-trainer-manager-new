package com.trainingmanagernew.ScheduleModule.Service.LocalJwtExtractor;

import java.util.UUID;

public interface ScheduleTokenExtraction {
    public UUID extractUuid(String token);
}
