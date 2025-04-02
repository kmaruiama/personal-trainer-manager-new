package com.trainingmanagernew.BodyModule.Service.HeightEntityService.Get;

import com.trainingmanagernew.BodyModule.Aspect.AuthorizeBodyModuleRequest;
import com.trainingmanagernew.BodyModule.Dto.Height.HeightGetDto;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Entity.HeightEntity;
import com.trainingmanagernew.BodyModule.Exception.BodyCustomExceptions;
import com.trainingmanagernew.BodyModule.Repository.HeightEntityRepository;
import com.trainingmanagernew.BodyModule.Service.InitializeBodyEntityOwner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GetAllHeightEntitiesByIdImpl implements GetAllHeightEntitiesById {
    private final InitializeBodyEntityOwner initializeBodyEntityOwner;
    private final HeightEntityRepository heightEntityRepository;

    public GetAllHeightEntitiesByIdImpl(InitializeBodyEntityOwner initializeBodyEntityOwner, HeightEntityRepository heightEntityRepository) {
        this.initializeBodyEntityOwner = initializeBodyEntityOwner;
        this.heightEntityRepository = heightEntityRepository;
    }

    @AuthorizeBodyModuleRequest
    @Override
    public List<HeightGetDto> get(UUID id) {
        BodyOwnerEntity bodyOwnerEntity = initializeBodyEntityOwner.initializeBodyOwnerEntity(id);
        List<HeightEntity> heightEntityList = heightEntityRepository.findAllByBodyOwnerEntity(bodyOwnerEntity);
        if (heightEntityList.isEmpty()){
            throw new BodyCustomExceptions.HeightEntityListIsEmpty();
        }
        return convert(heightEntityList);
    }

    private List<HeightGetDto> convert(List<HeightEntity> heightEntityList){
        List<HeightGetDto> heightGetDtoList = new ArrayList<>();
        for (int i = 0; i<heightEntityList.size(); i++){
            HeightGetDto heightGetDto = new HeightGetDto();
            heightGetDto.setDate(heightEntityList.get(i).getDate());
            heightGetDto.setHeight(heightEntityList.get(i).getHeight());
            heightGetDto.setId(heightEntityList.get(i).getId());
            heightGetDtoList.add(heightGetDto);
        }
        return heightGetDtoList;

    }
}
