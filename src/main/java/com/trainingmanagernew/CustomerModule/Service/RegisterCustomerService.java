package com.trainingmanagernew.CustomerModule.Service;

import com.trainingmanagernew.CustomerModule.Dto.CustomerInfoDto;
import com.trainingmanagernew.CustomerModule.Entity.CustomerEntity;
import com.trainingmanagernew.CustomerModule.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterCustomerService {
    private final CustomerRepository customerRepository;

    public RegisterCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void register(CustomerInfoDto customerInfoDto){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setNome(customerInfoDto.getNome());
        customerEntity.setDataNascimento(customerInfoDto.getDataNascimento());
        customerRepository.save(customerEntity);
    }
}
