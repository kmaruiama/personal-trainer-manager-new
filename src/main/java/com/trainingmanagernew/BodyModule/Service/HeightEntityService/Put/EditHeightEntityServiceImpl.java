package com.trainingmanagernew.BodyModule.Service.HeightEntityService.Put;

import com.trainingmanagernew.BodyModule.Aspect.AuthorizeBodyModuleRequest;
import com.trainingmanagernew.BodyModule.Dto.Height.HeightGetDto;
import com.trainingmanagernew.BodyModule.Entity.HeightEntity;
import com.trainingmanagernew.BodyModule.Exception.BodyCustomExceptions;
import com.trainingmanagernew.BodyModule.Repository.HeightEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditHeightEntityServiceImpl implements EditHeightEntityService {
    private final HeightEntityRepository heightEntityRepository;

    public EditHeightEntityServiceImpl(HeightEntityRepository heightEntityRepository) {
        this.heightEntityRepository = heightEntityRepository;
    }

    @AuthorizeBodyModuleRequest
    @Transactional
    @Override
    public void edit(HeightGetDto heightGetDto) {
        Optional<HeightEntity> heightEntityOptional = heightEntityRepository.findById(heightGetDto.getId());
        HeightEntity heightEntity;
        if (heightEntityOptional.isPresent()) {
            heightEntity = heightEntityOptional.get();
        } else {
            throw new BodyCustomExceptions.HeightEntityListIsEmpty();
        }
        changeParameters(heightGetDto, heightEntity);
    }

    private void changeParameters(HeightGetDto heightGetDto, HeightEntity heightEntity)
    {
        heightEntity.setHeight(heightGetDto.getHeight());
        heightEntity.setDate(heightGetDto.getDate());
    }
}
