package com.trainingmanagernew.CustomerModule.Service.LocalJwtExtractor;

import java.util.UUID;

public interface TokenExtraction {
    public UUID extractUuid(String token);
}
