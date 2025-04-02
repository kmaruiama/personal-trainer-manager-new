package com.trainingmanagernew.BodyModule.Service.BodyEntityService.Get;

import com.trainingmanagernew.BodyModule.Aspect.AuthorizeBodyModuleRequest;
import com.trainingmanagernew.BodyModule.Dto.Body.BodyGetDto;
import com.trainingmanagernew.BodyModule.Entity.BodyEntity;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Exception.BodyCustomExceptions;
import com.trainingmanagernew.BodyModule.Repository.BodyEntityRepository;
import com.trainingmanagernew.BodyModule.Repository.BodyOwnerEntityRepository;
import com.trainingmanagernew.BodyModule.Service.InitializeBodyEntityOwner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GetAllBodyEntitiesByIdImpl implements GetAllBodyEntitiesById {
    private final BodyEntityRepository bodyEntityRepository;
    private final BodyOwnerEntityRepository bodyOwnerEntityRepository;
    private final InitializeBodyEntityOwner initializeBodyEntityOwner;

    public GetAllBodyEntitiesByIdImpl(BodyEntityRepository bodyEntityRepository, BodyOwnerEntityRepository bodyOwnerEntityRepository, InitializeBodyEntityOwner initializeBodyEntityOwner) {
        this.bodyEntityRepository = bodyEntityRepository;
        this.bodyOwnerEntityRepository = bodyOwnerEntityRepository;
        this.initializeBodyEntityOwner = initializeBodyEntityOwner;
    }

    @AuthorizeBodyModuleRequest
    @Override
    public List<BodyGetDto> get(UUID id) {
        BodyOwnerEntity bodyOwnerEntity = initializeBodyEntityOwner.initializeBodyOwnerEntity(id);

        List<BodyEntity> bodyEntityList = bodyEntityRepository.findAllEntitiesByBodyOwnerEntity(bodyOwnerEntity);
        if (bodyEntityList.isEmpty()){
            throw new BodyCustomExceptions.BodyEntityListIsEmpty();
        }
        return convert(bodyEntityList);
    }

    private List<BodyGetDto> convert(List<BodyEntity> bodyEntityList){
        List<BodyGetDto> bodyGetDtoList = new ArrayList<>();
        for (int i = 0; i<bodyEntityList.size(); i++){
            BodyGetDto bodyGetDto = new BodyGetDto();
            bodyGetDto.setBodyFat(bodyEntityList.get(i).getBodyFat());
            bodyGetDto.setDate(bodyEntityList.get(i).getDate());
            bodyGetDto.setId(bodyEntityList.get(i).getId());
            bodyGetDto.setDate(bodyEntityList.get(i).getDate());
            bodyGetDtoList.add(bodyGetDto);
        }
        return bodyGetDtoList;
    }
}
