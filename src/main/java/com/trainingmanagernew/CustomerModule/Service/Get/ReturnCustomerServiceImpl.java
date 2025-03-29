package com.trainingmanagernew.CustomerModule.Service.Get;

import com.trainingmanagernew.CustomerModule.Dto.CustomerGetDto;
import com.trainingmanagernew.CustomerModule.Entity.CustomerEntity;
import com.trainingmanagernew.CustomerModule.Entity.CustomerEntityOwner;
import com.trainingmanagernew.CustomerModule.Exception.CustomerCustomExceptions;
import com.trainingmanagernew.CustomerModule.Repository.CustomerOwnerRepository;
import com.trainingmanagernew.CustomerModule.Repository.CustomerRepository;
import com.trainingmanagernew.CustomerModule.Service.LocalJwtExtractor.TokenExtraction;
import com.trainingmanagernew.CustomerModule.Service.LocalJwtExtractor.TokenExtractionMethodsImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReturnCustomerServiceImpl implements ReturnCustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerOwnerRepository customerOwnerRepository;
    private final TokenExtraction tokenExtraction;

    public ReturnCustomerServiceImpl(CustomerRepository customerRepository, CustomerOwnerRepository customerOwnerRepository, TokenExtraction tokenExtraction) {
        this.customerRepository = customerRepository;
        this.customerOwnerRepository = customerOwnerRepository;
        this.tokenExtraction = tokenExtraction;
    }

    @Override
    public List<CustomerGetDto> returnAllCustomers(String token) {
        UUID ownerUUID = tokenExtraction.extractUuid(token);
        Optional<CustomerEntityOwner> customerEntityOwnerOptional = customerOwnerRepository.findByOwnerId(ownerUUID);
        CustomerEntityOwner customerEntityOwner;
        if (customerEntityOwnerOptional.isPresent()){
            customerEntityOwner = customerEntityOwnerOptional.get();
        }
        else{
            throw new CustomerCustomExceptions.OwnerNotFoundException();
        }
        List<CustomerEntity> customerEntityList = customerRepository.findAllByLocalOwner(customerEntityOwner);

        return convert(customerEntityList);
    }

    private List<CustomerGetDto> convert(List<CustomerEntity> customerEntityList){
        List<CustomerGetDto> customerGetDtoList = new ArrayList<>();
        for (int i = 0; i<customerEntityList.size(); i++){
            CustomerGetDto customerGetDto = new CustomerGetDto();
            customerGetDto.setId(customerEntityList.get(i).getId());
            customerGetDto.setNome(customerEntityList.get(i).getName());
            customerGetDto.setDataNascimento(customerEntityList.get(i).getBirthDate());
            customerGetDtoList.add(customerGetDto);
        }
        return customerGetDtoList;
    }
}
