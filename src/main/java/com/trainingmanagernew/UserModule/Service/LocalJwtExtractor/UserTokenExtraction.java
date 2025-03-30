package com.trainingmanagernew.UserModule.Service.LocalJwtExtractor;

import java.util.UUID;

public interface UserTokenExtraction {
    public UUID extractUuid(String token);
}
