package com.trainingmanagernew.CustomerModule.Service;

import com.trainingmanagernew.CustomerModule.Dto.CustomerInfoDto;
import com.trainingmanagernew.CustomerModule.Entity.CustomerEntity;
import com.trainingmanagernew.CustomerModule.Entity.CustomerEntityOwner;
import com.trainingmanagernew.CustomerModule.Exception.CustomerCustomExceptions;
import com.trainingmanagernew.CustomerModule.Repository.CustomerOwnerRepository;
import com.trainingmanagernew.CustomerModule.Repository.CustomerRepository;
import com.trainingmanagernew.CustomerModule.Service.LocalJwtExtractor.TokenExtraction;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegisterCustomerService {
    private final CustomerRepository customerRepository;
    private final TokenExtraction tokenExtraction;
    private final CustomerOwnerRepository customerOwnerRepository;

    public RegisterCustomerService(CustomerRepository customerRepository, TokenExtraction tokenExtraction, CustomerOwnerRepository customerOwnerRepository) {
        this.customerRepository = customerRepository;
        this.tokenExtraction = tokenExtraction;
        this.customerOwnerRepository = customerOwnerRepository;
    }

    public void register(CustomerInfoDto customerInfoDto, String authHeader){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customerInfoDto.getName());
        customerEntity.setBirthDate(customerInfoDto.getBirthDate());
        setLocalOwner(customerEntity, authHeader);
        customerRepository.save(customerEntity);
    }

    private void setLocalOwner(CustomerEntity customerEntity, String authHeader){
        UUID uuid = tokenExtraction.extractUuid(authHeader);
        Optional<CustomerEntityOwner> customerEntityOwnerOptional = customerOwnerRepository.findByOwnerId(uuid);
        CustomerEntityOwner customerEntityOwner;
        if (customerEntityOwnerOptional.isPresent()){
            customerEntityOwner = customerEntityOwnerOptional.get();
        }
        else {
            throw new CustomerCustomExceptions.OwnerNotFoundException();
        }
        customerEntity.setLocalOwner(customerEntityOwner);
    }
}
