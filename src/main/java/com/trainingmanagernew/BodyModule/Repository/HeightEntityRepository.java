package com.trainingmanagernew.BodyModule.Repository;

import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import com.trainingmanagernew.BodyModule.Entity.HeightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HeightEntityRepository extends JpaRepository <HeightEntity, UUID> {
    List<HeightEntity> findAllByBodyOwnerEntity(BodyOwnerEntity bodyOwnerEntity);
}
