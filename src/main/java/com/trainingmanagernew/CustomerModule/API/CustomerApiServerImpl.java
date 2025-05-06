package com.trainingmanagernew.CustomerModule.API;

import com.trainingmanagernew.CustomerModule.Entity.CustomerEntity;
import com.trainingmanagernew.CustomerModule.Exception.CustomerCustomExceptions;
import com.trainingmanagernew.CustomerModule.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerApiServerImpl implements CustomerApiServer{
    private final CustomerRepository customerRepository;

    public CustomerApiServerImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public String serveCustomerName(UUID customerId) {
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(customerId);
        if (customerEntityOptional.isPresent()){
            return customerEntityOptional.get().getName();
        }
        else{
             throw new CustomerCustomExceptions.CustomerNotFoundException();
        }
    }
}
