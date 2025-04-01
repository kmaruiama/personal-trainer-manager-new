package com.trainingmanagernew.BodyModule.Service.Post;

import com.trainingmanagernew.BodyModule.Aspect.AuthorizeBodyModuleRequest;
import com.trainingmanagernew.BodyModule.Dto.BodyPostDto;
import com.trainingmanagernew.BodyModule.Entity.BodyEntity;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Exception.BodyCustomExceptions;
import com.trainingmanagernew.BodyModule.Repository.BodyEntityRepository;
import com.trainingmanagernew.BodyModule.Repository.BodyOwnerEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddNewBodyRecordServiceImpl implements AddNewBodyRecordService {
    private final BodyEntityRepository bodyEntityRepository;
    private final BodyOwnerEntityRepository bodyOwnerEntityRepository;

    public AddNewBodyRecordServiceImpl(BodyEntityRepository bodyEntityRepository, BodyOwnerEntityRepository bodyOwnerEntityRepository) {
        this.bodyEntityRepository = bodyEntityRepository;
        this.bodyOwnerEntityRepository = bodyOwnerEntityRepository;
    }

    @AuthorizeBodyModuleRequest
    @Transactional
    @Override
    public void add(BodyPostDto bodyPostDto){
        BodyEntity bodyEntity = new BodyEntity();
        bodyEntity.setDate(bodyPostDto.getDate());
        bodyEntity.setWeight(bodyPostDto.getWeight());
        bodyEntity.setBodyFat(bodyPostDto.getBodyFat());
        setBodyEntityOwner(bodyEntity, bodyPostDto.getBodyOwnerId());
        bodyEntityRepository.save(bodyEntity);
    }

    private void setBodyEntityOwner(BodyEntity bodyEntity, UUID id){
        BodyOwnerEntity bodyOwnerEntity;
        Optional<BodyOwnerEntity> bodyOwnerEntityOptional = bodyOwnerEntityRepository.findById(id);
        if (bodyOwnerEntityOptional.isPresent()){
            bodyOwnerEntity = bodyOwnerEntityOptional.get();
            bodyEntity.setBodyOwnerEntity(bodyOwnerEntity);
        }
        else {
            throw new BodyCustomExceptions.BodyOwnerEntityNotFound();
        }
    }

}
