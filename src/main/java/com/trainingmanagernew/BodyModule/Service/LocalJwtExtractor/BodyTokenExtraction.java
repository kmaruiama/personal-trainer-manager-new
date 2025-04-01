package com.trainingmanagernew.BodyModule.Service.LocalJwtExtractor;

import java.util.UUID;

public interface BodyTokenExtraction {
    public UUID extractUuid(String token);
}
