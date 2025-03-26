package com.trainingmanagernew.Security.Controller;

import com.trainingmanagernew.Security.Service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    //mudar para login based
    public String token (Authentication authentication){
        return tokenService.generateToken(authentication);
    }
}
