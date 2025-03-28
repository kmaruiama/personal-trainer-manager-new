package com.trainingmanagernew.SecurityModule.Controller;

import com.trainingmanagernew.SecurityModule.Dto.LoginDto;
import com.trainingmanagernew.SecurityModule.Service.LoginAuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final LoginAuthenticationService loginAuthenticationService;

    public AuthController(LoginAuthenticationService loginAuthenticationService) {
        this.loginAuthenticationService = loginAuthenticationService;
    }

    @PostMapping("/login")
    //mudar para login based
    public String token (@RequestBody LoginDto loginDto){
       return loginAuthenticationService.returnTokenIfLoginIsValid(loginDto);
    }

    @GetMapping
    public String testingTokenValidation(@RequestHeader("Authorization") String authHeader){
        return "AIII";
    }

}
