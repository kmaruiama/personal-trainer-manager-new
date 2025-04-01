package com.trainingmanagernew.BodyModule.Service.Put;

import com.trainingmanagernew.BodyModule.Dto.BodyPostDto;
import com.trainingmanagernew.BodyModule.Entity.BodyEntity;
import com.trainingmanagernew.BodyModule.Exception.BodyCustomExceptions;
import com.trainingmanagernew.BodyModule.Repository.BodyEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditBodyEntityServiceImpl implements EditBodyEntityService{
    private final BodyEntityRepository bodyEntityRepository;

    public EditBodyEntityServiceImpl(BodyEntityRepository bodyEntityRepository) {
        this.bodyEntityRepository = bodyEntityRepository;
    }

    @Transactional
    @Override
    public void edit(BodyPostDto bodyPostDto) {
        Optional<BodyEntity> bodyEntityOptional = bodyEntityRepository.findById(bodyPostDto.getOptionalBodyEntityId());
        BodyEntity bodyEntity;

        if (bodyEntityOptional.isPresent()){
            bodyEntity = bodyEntityOptional.get();
        }
        else {
            throw new BodyCustomExceptions.BodyEntityNotFound();
        }

        changeParameters(bodyEntity, bodyPostDto);
    }

    private void changeParameters(BodyEntity bodyEntity, BodyPostDto bodyPostDto){
        bodyEntity.setBodyFat(bodyPostDto.getBodyFat());
        bodyEntity.setWeight(bodyPostDto.getWeight());
        bodyEntity.setDate(bodyPostDto.getDate());
    }
}
