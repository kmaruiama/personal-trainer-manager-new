package com.trainingmanagernew.BodyModule.Service.BodyEntityService.Delete;

import com.trainingmanagernew.BodyModule.Aspect.AuthorizeBodyResourceRequest;
import com.trainingmanagernew.BodyModule.Dto.Shared.DeleteResourceDto;
import com.trainingmanagernew.BodyModule.Repository.BodyEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteBodyEntityServiceImpl implements DeleteBodyEntityService{
    private final BodyEntityRepository bodyEntityRepository;

    public DeleteBodyEntityServiceImpl(BodyEntityRepository bodyEntityRepository) {
        this.bodyEntityRepository = bodyEntityRepository;
    }

    @AuthorizeBodyResourceRequest
    @Override
    public void delete(DeleteResourceDto deleteResourceDto){
        bodyEntityRepository.deleteById(deleteResourceDto.getId());
    }
}
