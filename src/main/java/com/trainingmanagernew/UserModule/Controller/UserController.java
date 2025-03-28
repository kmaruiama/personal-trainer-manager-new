package com.trainingmanagernew.UserModule.Controller;


import com.trainingmanagernew.UserModule.Dto.RegisterDto;
import com.trainingmanagernew.UserModule.Service.Delete.DeleteUserService;
import com.trainingmanagernew.UserModule.Service.Register.RegisterNewUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("api/user")
public class UserController {

    private final RegisterNewUserService registerNewUserService;
    private final DeleteUserService deleteUserService;

    UserController(RegisterNewUserService registerNewUserService, DeleteUserService deleteUserService){
        this.registerNewUserService = registerNewUserService;
        this.deleteUserService = deleteUserService;
    }

    //requisitions that must cascade a complete deletion in all modules of the application
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto registerDto){
        registerNewUserService.register(registerDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Usuário registrado com sucesso."));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> delete (@RequestParam UUID userId){
        deleteUserService.delete(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Usuário deletado com sucesso."));
    }

}
