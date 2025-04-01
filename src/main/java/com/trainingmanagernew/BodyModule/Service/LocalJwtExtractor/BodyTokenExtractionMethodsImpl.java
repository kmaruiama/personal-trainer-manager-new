package com.trainingmanagernew.BodyModule.Service.LocalJwtExtractor;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.trainingmanagernew.CustomerModule.Exception.CustomerCustomExceptions;
import com.trainingmanagernew.CustomerModule.Service.LocalJwtExtractor.TokenExtraction;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.UUID;

@Component
public class BodyTokenExtractionMethodsImpl implements BodyTokenExtraction {

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
            throw new CustomerCustomExceptions.TokenParsingExceptionAtCustomerModule();
        }

        return UUID.fromString(uuidString);
    }

}
