package com.trainingmanagernew.CustomerModule.Service;

import com.trainingmanagernew.CustomerModule.Repository.CustomerOwnerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCustomerOwnerService {
    private final CustomerOwnerRepository customerOwnerRepository;

    public DeleteCustomerOwnerService(CustomerOwnerRepository customerOwnerRepository) {
        this.customerOwnerRepository = customerOwnerRepository;
    }

    public void delete(UUID ownerId){
        customerOwnerRepository.deleteByOwnerId(ownerId);
    }
}
