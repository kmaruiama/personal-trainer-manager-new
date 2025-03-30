package com.trainingmanagernew.UserModule.Controller;

import com.trainingmanagernew.UserModule.Dto.RegisterDto;
import com.trainingmanagernew.UserModule.Dto.UserDto;
import com.trainingmanagernew.UserModule.Service.Delete.DeleteUserService;
import com.trainingmanagernew.UserModule.Service.Get.GetUserService;
import com.trainingmanagernew.UserModule.Service.Put.PutUserService;
import com.trainingmanagernew.UserModule.Service.Register.RegisterNewUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("api/user")
@Validated
public class UserController {

    private final RegisterNewUserService registerNewUserService;
    private final DeleteUserService deleteUserService;
    private final GetUserService getUserService;
    private final PutUserService putUserService;

    UserController(RegisterNewUserService registerNewUserService, DeleteUserService deleteUserService, GetUserService getUserService, PutUserService putUserService){
        this.registerNewUserService = registerNewUserService;
        this.deleteUserService = deleteUserService;
        this.getUserService = getUserService;
        this.putUserService = putUserService;
    }

    //requisitions that must cascade a complete deletion in all modules of the application
    //I'll probably move this to the security module
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto registerDto){
        registerNewUserService.register(registerDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Usuário registrado com sucesso."));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteUser(@RequestParam UUID userId){
        deleteUserService.delete(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Usuário deletado com sucesso."));
    }

    @GetMapping("/get")
    public ResponseEntity<UserDto> getUser(@RequestHeader ("Authorization") String authHeader){
        UserDto userDto = getUserService.get(authHeader);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }


    //I won't be patching small entities
    @PutMapping("/edit")
    public ResponseEntity<Map<String, String>> editUser(@RequestBody UserDto userDto,
            @RequestHeader("Authorization") String authHeader){
        putUserService.put(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Usuário editado com sucesso."));
    }

}
