package com.trainingmanagernew.CustomerModule.Service;

import com.trainingmanagernew.CustomerModule.Entity.CustomerEntityOwner;
import com.trainingmanagernew.CustomerModule.Repository.CustomerOwnerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterCustomerOwnerService {
    private final CustomerOwnerRepository customerOwnerRepository;

    public RegisterCustomerOwnerService(CustomerOwnerRepository customerOwnerRepository) {
        this.customerOwnerRepository = customerOwnerRepository;
    }

    public void register(UUID ownerId){
        CustomerEntityOwner customerEntityOwner = new CustomerEntityOwner();
        customerEntityOwner.setOwnerId(ownerId);
        customerOwnerRepository.save(customerEntityOwner);
    }
}
