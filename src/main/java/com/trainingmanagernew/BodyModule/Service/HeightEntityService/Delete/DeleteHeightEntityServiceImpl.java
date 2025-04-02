package com.trainingmanagernew.BodyModule.Service.HeightEntityService.Delete;

import com.trainingmanagernew.BodyModule.Aspect.AuthorizeBodyModuleRequest;
import com.trainingmanagernew.BodyModule.Repository.HeightEntityRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteHeightEntityServiceImpl implements DeleteHeightEntityService{
    private final HeightEntityRepository heightEntityRepository;

    public DeleteHeightEntityServiceImpl(HeightEntityRepository heightEntityRepository) {
        this.heightEntityRepository = heightEntityRepository;
    }

    @AuthorizeBodyModuleRequest
    @Override
    public void delete(UUID id) {
        heightEntityRepository.deleteById(id);
    }
}
