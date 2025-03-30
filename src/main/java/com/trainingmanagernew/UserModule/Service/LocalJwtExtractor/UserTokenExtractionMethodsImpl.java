package com.trainingmanagernew.UserModule.Service.LocalJwtExtractor;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.trainingmanagernew.CustomerModule.Exception.CustomerCustomExceptions;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.UUID;

@Component
public class UserTokenExtractionMethodsImpl implements UserTokenExtraction {

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
