package com.trainingmanagernew.BodyModule.Service.Delete;

import com.trainingmanagernew.BodyModule.Aspect.AuthorizeBodyModuleRequest;
import com.trainingmanagernew.BodyModule.Repository.BodyEntityRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteBodyEntityServiceImpl implements DeleteBodyEntityService{
    private final BodyEntityRepository bodyEntityRepository;

    public DeleteBodyEntityServiceImpl(BodyEntityRepository bodyEntityRepository) {
        this.bodyEntityRepository = bodyEntityRepository;
    }

    @AuthorizeBodyModuleRequest
    @Override
    public void delete(UUID id){
        bodyEntityRepository.deleteById(id);
    }
}
