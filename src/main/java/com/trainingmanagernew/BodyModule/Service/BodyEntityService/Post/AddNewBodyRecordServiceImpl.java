package com.trainingmanagernew.BodyModule.Service.BodyEntityService.Post;

import com.trainingmanagernew.BodyModule.Aspect.AuthorizeBodyModuleRequest;
import com.trainingmanagernew.BodyModule.Dto.Body.BodyPostDto;
import com.trainingmanagernew.BodyModule.Entity.BodyEntity;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Repository.BodyEntityRepository;
import com.trainingmanagernew.BodyModule.Repository.BodyOwnerEntityRepository;
import com.trainingmanagernew.BodyModule.Service.BodyOwnerEntityService.Get.InitializeBodyEntityOwner;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AddNewBodyRecordServiceImpl implements AddNewBodyRecordService {
    private final BodyEntityRepository bodyEntityRepository;
    private final BodyOwnerEntityRepository bodyOwnerEntityRepository;
    private final InitializeBodyEntityOwner initializeBodyEntityOwner;

    public AddNewBodyRecordServiceImpl(BodyEntityRepository bodyEntityRepository, BodyOwnerEntityRepository bodyOwnerEntityRepository, InitializeBodyEntityOwner initializeBodyEntityOwner) {
        this.bodyEntityRepository = bodyEntityRepository;
        this.bodyOwnerEntityRepository = bodyOwnerEntityRepository;
        this.initializeBodyEntityOwner = initializeBodyEntityOwner;
    }

    @AuthorizeBodyModuleRequest
    @Transactional
    @Override
    public void add(BodyPostDto bodyPostDto){
        BodyEntity bodyEntity = new BodyEntity();
        bodyEntity.setDate(bodyPostDto.getDate());
        bodyEntity.setWeight(bodyPostDto.getWeight());
        bodyEntity.setBodyFat(bodyPostDto.getBodyFat());
        BodyOwnerEntity bodyOwnerEntity = initializeBodyEntityOwner.initialize(bodyPostDto.getBodyOwnerId());
        bodyEntity.setBodyOwnerEntity(bodyOwnerEntity);
        bodyEntityRepository.save(bodyEntity);
    }
}
