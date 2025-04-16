package com.trainingmanagernew.BodyModule.Service.BodyOwnerEntityService.Get;


import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Exception.BodyCustomExceptions;
import com.trainingmanagernew.BodyModule.Repository.BodyOwnerEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetBodyOwnerIdFromCustomerIdImpl implements GetBodyOwnerIdFromCustomerId {
    private final BodyOwnerEntityRepository bodyOwnerEntityRepository;

    public GetBodyOwnerIdFromCustomerIdImpl(BodyOwnerEntityRepository bodyOwnerEntityRepository) {
        this.bodyOwnerEntityRepository = bodyOwnerEntityRepository;
    }

    @Override
    public UUID get(UUID customerId) {
        Optional<BodyOwnerEntity> bodyOwnerEntityOptional = bodyOwnerEntityRepository.findByCustomerId(customerId);
        if (bodyOwnerEntityOptional.isPresent()){
            return bodyOwnerEntityOptional.get().getId();
        }
        else {
            throw new BodyCustomExceptions.BodyOwnerEntityNotFound();
        }
    }
}
