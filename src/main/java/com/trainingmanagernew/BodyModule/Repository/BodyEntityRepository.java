package com.trainingmanagernew.BodyModule.Repository;

import com.trainingmanagernew.BodyModule.Entity.BodyEntity;
import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BodyEntityRepository extends JpaRepository<BodyEntity, UUID> {
    List<BodyEntity> findAllEntitiesByBodyOwnerEntity(BodyOwnerEntity bodyOwnerEntity);

    Optional<BodyEntity> findFirstByBodyOwnerEntityOrderByDateDesc(BodyOwnerEntity bodyOwnerEntity);
}
