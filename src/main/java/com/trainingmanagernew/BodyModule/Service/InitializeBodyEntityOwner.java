package com.trainingmanagernew.BodyModule.Service;

import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Exception.BodyCustomExceptions;
import com.trainingmanagernew.BodyModule.Repository.BodyOwnerEntityRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class InitializeBodyEntityOwner {
    private final BodyOwnerEntityRepository bodyOwnerEntityRepository;

    public InitializeBodyEntityOwner(BodyOwnerEntityRepository bodyOwnerEntityRepository) {
        this.bodyOwnerEntityRepository = bodyOwnerEntityRepository;
    }

    public BodyOwnerEntity initializeBodyOwnerEntity(UUID uuid){
        Optional<BodyOwnerEntity> bodyOwnerEntityOptional = bodyOwnerEntityRepository.findById(uuid);
        if (bodyOwnerEntityOptional.isPresent()){
            return bodyOwnerEntityOptional.get();
        }
        else {
            throw new BodyCustomExceptions.BodyOwnerEntityNotFound();
        }
    }
}
