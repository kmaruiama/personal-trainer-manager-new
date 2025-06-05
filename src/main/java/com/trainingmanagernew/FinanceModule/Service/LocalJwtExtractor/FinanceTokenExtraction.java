package com.trainingmanagernew.FinanceModule.Service.LocalJwtExtractor;

import java.util.UUID;

public interface FinanceTokenExtraction {
    public UUID extractUuid(String token);
}
