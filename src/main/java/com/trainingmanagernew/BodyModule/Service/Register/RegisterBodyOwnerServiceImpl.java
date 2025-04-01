package com.trainingmanagernew.BodyModule.Service.Register;

import com.trainingmanagernew.BodyModule.Dto.BodyIdTransferDto;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Repository.BodyOwnerEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RegisterBodyOwnerServiceImpl implements RegisterBodyOwnerService{
    private final BodyOwnerEntityRepository bodyOwnerEntityRepository;

    public RegisterBodyOwnerServiceImpl(BodyOwnerEntityRepository bodyOwnerEntityRepository) {
        this.bodyOwnerEntityRepository = bodyOwnerEntityRepository;
    }

    @Override
    public void register(BodyIdTransferDto bodyIdTransferDto) {
        BodyOwnerEntity bodyOwnerEntity = new BodyOwnerEntity();
        bodyOwnerEntity.setCustomerId(bodyIdTransferDto.getCustomerId());
        bodyOwnerEntity.setCustomerOwnerId(bodyIdTransferDto.getCustomerOwnerId());
        bodyOwnerEntityRepository.save(bodyOwnerEntity);
    }
}
