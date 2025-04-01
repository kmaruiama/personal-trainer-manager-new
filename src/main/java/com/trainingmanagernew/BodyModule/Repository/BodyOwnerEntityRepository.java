package com.trainingmanagernew.BodyModule.Repository;

import com.trainingmanagernew.BodyModule.Entity.BodyOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BodyOwnerEntityRepository extends JpaRepository<BodyOwnerEntity, UUID> {
}
