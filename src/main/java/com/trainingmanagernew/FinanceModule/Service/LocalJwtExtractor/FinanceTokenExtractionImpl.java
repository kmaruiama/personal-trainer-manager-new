package com.trainingmanagernew.FinanceModule.Service.LocalJwtExtractor;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.trainingmanagernew.FinanceModule.Exception.FinanceCustomExceptions;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.UUID;

@Component
public class FinanceTokenExtractionImpl implements FinanceTokenExtraction {
    public UUID extractUuid(String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String uuidString;

        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            uuidString = claimsSet.getStringClaim("uuid");
        } catch (ParseException e){
            throw new FinanceCustomExceptions.TokenParsingExceptionAtFinanceModule();
        }

        return UUID.fromString(uuidString);
    }
}
