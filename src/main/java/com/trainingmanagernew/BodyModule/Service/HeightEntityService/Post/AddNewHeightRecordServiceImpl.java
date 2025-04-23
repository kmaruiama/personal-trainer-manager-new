package com.trainingmanagernew.BodyModule.Service.HeightEntityService.Post;

import com.trainingmanagernew.BodyModule.Aspect.AuthorizeBodyModuleRequest;
import com.trainingmanagernew.BodyModule.Dto.Height.HeightPostDto;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Entity.HeightEntity;
import com.trainingmanagernew.BodyModule.Repository.HeightEntityRepository;
import com.trainingmanagernew.BodyModule.Service.BodyOwnerEntityService.Get.InitializeBodyEntityOwner;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddNewHeightRecordServiceImpl  implements AddNewHeightRecordService {
    private final HeightEntityRepository heightEntityRepository;
    private final InitializeBodyEntityOwner initializeBodyEntityOwner;

    public AddNewHeightRecordServiceImpl(HeightEntityRepository heightEntityRepository,
                                         InitializeBodyEntityOwner initializeBodyEntityOwner) {
        this.heightEntityRepository = heightEntityRepository;
        this.initializeBodyEntityOwner = initializeBodyEntityOwner;
    }

    @AuthorizeBodyModuleRequest
    @Transactional
    @Override
    public void add(HeightPostDto heightPostDto) {
        HeightEntity heightEntity = new HeightEntity();
        heightEntity.setHeight(heightPostDto.getHeight());
        heightEntity.setDate(heightPostDto.getDate());
        addHeightEntityOwner(heightEntity, heightPostDto.getBodyOwnerId());
        heightEntityRepository.save(heightEntity);
    }

    private void addHeightEntityOwner(HeightEntity heightEntity, UUID id){
        BodyOwnerEntity bodyOwnerEntity = initializeBodyEntityOwner.initialize(id);
        heightEntity.setBodyOwnerEntity(bodyOwnerEntity);
    }
}
