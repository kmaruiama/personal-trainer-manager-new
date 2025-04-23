package com.trainingmanagernew.ScheduleModule.Repository;

import com.trainingmanagernew.ScheduleModule.Entity.ScheduleOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScheduleOwnerRepository extends JpaRepository<ScheduleOwnerEntity, UUID> {
}
