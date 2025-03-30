package com.trainingmanagernew.CustomerModule.Controller;

import com.trainingmanagernew.CustomerModule.Dto.CustomerGetDto;
import com.trainingmanagernew.CustomerModule.Dto.CustomerInfoDto;
import com.trainingmanagernew.CustomerModule.Service.Get.ReturnCustomerService;
import com.trainingmanagernew.CustomerModule.Service.Register.RegisterCustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/customer")
@Validated
public class CustomerController {

    private final RegisterCustomerService registerCustomerService;
    private final ReturnCustomerService returnCustomerService;

    public CustomerController(RegisterCustomerService registerCustomerService,
                              ReturnCustomerService returnCustomerService) {
        this.registerCustomerService = registerCustomerService;
        this.returnCustomerService = returnCustomerService;
    }

    @PostMapping("/register")
    ResponseEntity<Map<String, String>> registerNewCustomer (@Valid @RequestBody CustomerInfoDto customerInfoDto,
                                                             @RequestHeader("Authorization") String authHeader){
        registerCustomerService.register(customerInfoDto, authHeader);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Usuário registrado com sucesso."));
    }

    @GetMapping("/all")
    ResponseEntity<List<CustomerGetDto>> returnAllCustomers(@RequestHeader("Authorization") String authHeader){
        List<CustomerGetDto> customerInfoDtoList = returnCustomerService.returnAllCustomers(authHeader);
        return ResponseEntity.status(HttpStatus.OK).body(customerInfoDtoList);
    }
}
