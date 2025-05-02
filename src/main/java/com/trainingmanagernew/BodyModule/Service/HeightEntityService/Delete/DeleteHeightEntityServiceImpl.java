package com.trainingmanagernew.BodyModule.Service.HeightEntityService.Delete;

import com.trainingmanagernew.BodyModule.Aspect.AuthorizeBodyResourceRequest;
import com.trainingmanagernew.BodyModule.Dto.Shared.DeleteResourceDto;
import com.trainingmanagernew.BodyModule.Repository.HeightEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteHeightEntityServiceImpl implements DeleteHeightEntityService{
    private final HeightEntityRepository heightEntityRepository;

    public DeleteHeightEntityServiceImpl(HeightEntityRepository heightEntityRepository) {
        this.heightEntityRepository = heightEntityRepository;
    }

    @AuthorizeBodyResourceRequest
    @Override
    public void delete(DeleteResourceDto deleteResourceDto) {
        heightEntityRepository.deleteById(deleteResourceDto.getId());
    }
}
