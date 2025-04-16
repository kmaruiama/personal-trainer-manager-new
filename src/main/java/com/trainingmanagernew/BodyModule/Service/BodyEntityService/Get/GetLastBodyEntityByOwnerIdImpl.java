package com.trainingmanagernew.BodyModule.Service.BodyEntityService.Get;

import com.trainingmanagernew.BodyModule.Dto.Body.BodyGetDto;
import com.trainingmanagernew.BodyModule.Entity.BodyEntity;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Exception.BodyCustomExceptions;
import com.trainingmanagernew.BodyModule.Repository.BodyEntityRepository;
import com.trainingmanagernew.BodyModule.Repository.BodyOwnerEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetLastBodyEntityByOwnerIdImpl implements GetLastBodyEntityByOwnerId {
    private final BodyOwnerEntityRepository bodyOwnerEntityRepository;
    private final BodyEntityRepository bodyEntityRepository;

    public GetLastBodyEntityByOwnerIdImpl(BodyOwnerEntityRepository bodyOwnerEntityRepository, BodyEntityRepository bodyEntityRepository) {
        this.bodyOwnerEntityRepository = bodyOwnerEntityRepository;
        this.bodyEntityRepository = bodyEntityRepository;
    }

    @Override
    public BodyGetDto get(UUID bodyOwnerId) {
        BodyOwnerEntity bodyOwnerEntity;
        Optional<BodyOwnerEntity> bodyOwnerEntityOptional = bodyOwnerEntityRepository.findById(bodyOwnerId);
        if (bodyOwnerEntityOptional.isPresent()){
            bodyOwnerEntity = bodyOwnerEntityOptional.get();
        }
        else {
            throw new BodyCustomExceptions.BodyOwnerEntityNotFound();
        }

        Optional<BodyEntity> bodyEntityOptional = bodyEntityRepository.findFirstByBodyOwnerEntityOrderByDateDesc(bodyOwnerEntity);
        BodyEntity bodyEntity;
        if (bodyEntityOptional.isPresent()){
            bodyEntity = bodyEntityOptional.get();
        }
        else {
            throw new BodyCustomExceptions.BodyEntityNotFound();
        }
        return convert(bodyEntity);
    }

    private BodyGetDto convert(BodyEntity bodyEntity){
        BodyGetDto bodyGetDto = new BodyGetDto();
        bodyGetDto.setDate(bodyEntity.getDate());
        bodyGetDto.setId(bodyEntity.getId());
        bodyGetDto.setBodyFat(bodyEntity.getBodyFat());
        bodyGetDto.setWeight(bodyEntity.getWeight());
        return bodyGetDto;
    }
}
