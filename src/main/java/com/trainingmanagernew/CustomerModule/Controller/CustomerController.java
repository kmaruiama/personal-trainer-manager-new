package com.trainingmanagernew.CustomerModule.Controller;

import com.trainingmanagernew.CustomerModule.Dto.CustomerInfoDto;
import com.trainingmanagernew.CustomerModule.Service.RegisterCustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    private final RegisterCustomerService registerCustomerService;

    public CustomerController(RegisterCustomerService registerCustomerService) {
        this.registerCustomerService = registerCustomerService;
    }

    @PostMapping("/register")
    ResponseEntity<Map<String, String>> registerNewCustomer (@RequestBody CustomerInfoDto customerInfoDto,
                                                             @RequestHeader("Authorization") String authHeader){
        registerCustomerService.register(customerInfoDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Usuário registrado com sucesso."));
    }

}
