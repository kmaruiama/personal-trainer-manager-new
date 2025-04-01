package com.trainingmanagernew.CustomerModule.Service.Register;

import com.trainingmanagernew.CustomerModule.CustomerEventEmitter;
import com.trainingmanagernew.CustomerModule.Dto.CustomerIdTransferDto;
import com.trainingmanagernew.CustomerModule.Dto.CustomerInfoDto;
import com.trainingmanagernew.CustomerModule.Entity.CustomerEntity;
import com.trainingmanagernew.CustomerModule.Entity.CustomerEntityOwner;
import com.trainingmanagernew.CustomerModule.Exception.CustomerCustomExceptions;
import com.trainingmanagernew.CustomerModule.Repository.CustomerOwnerRepository;
import com.trainingmanagernew.CustomerModule.Repository.CustomerRepository;
import com.trainingmanagernew.CustomerModule.Service.LocalJwtExtractor.TokenExtraction;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegisterCustomerService {
    private final CustomerRepository customerRepository;
    private final TokenExtraction tokenExtraction;
    private final CustomerOwnerRepository customerOwnerRepository;
    private final CustomerEventEmitter customerEventEmitter;

    private UUID ownerUUID;

    public RegisterCustomerService(CustomerRepository customerRepository, TokenExtraction tokenExtraction, CustomerOwnerRepository customerOwnerRepository, CustomerEventEmitter customerEventEmitter) {
        this.customerRepository = customerRepository;
        this.tokenExtraction = tokenExtraction;
        this.customerOwnerRepository = customerOwnerRepository;
        this.customerEventEmitter = customerEventEmitter;
    }

    @Transactional
    public void register(CustomerInfoDto customerInfoDto, String authHeader){
        setOwnerUUID(authHeader);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customerInfoDto.getName());
        customerEntity.setBirthDate(customerInfoDto.getBirthDate());
        setLocalOwner(customerEntity);
        customerEntity = customerRepository.save(customerEntity);

        emitSuccessfulRegistration(customerEntity);
    }

    private void setOwnerUUID(String authHeader){
        this.ownerUUID = tokenExtraction.extractUuid(authHeader);
    }

    private void setLocalOwner(CustomerEntity customerEntity){
        Optional<CustomerEntityOwner> customerEntityOwnerOptional = customerOwnerRepository.findByOwnerId(ownerUUID);
        CustomerEntityOwner customerEntityOwner;
        if (customerEntityOwnerOptional.isPresent()){
            customerEntityOwner = customerEntityOwnerOptional.get();
        }
        else {
            throw new CustomerCustomExceptions.OwnerNotFoundException();
        }
        customerEntity.setLocalOwner(customerEntityOwner);
    }

    private void emitSuccessfulRegistration(CustomerEntity customerEntity){
        CustomerIdTransferDto customerIdTransferDto = new CustomerIdTransferDto();
        customerIdTransferDto.setCustomerId(customerEntity.getId());
        customerIdTransferDto.setCustomerOwnerId(ownerUUID);
        customerEventEmitter.customerRegistered(customerIdTransferDto);
    }
}
