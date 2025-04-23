package com.trainingmanagernew.ScheduleModule.Repository;

import com.trainingmanagernew.ScheduleModule.Entity.ScheduleEntity;
import com.trainingmanagernew.ScheduleModule.Entity.ScheduleOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, UUID> {

    List<ScheduleEntity> findByScheduleOwnerEntity_Id(UUID scheduleOwnerId);

    List<ScheduleEntity> findAllByScheduleOwnerEntity(ScheduleOwnerEntity scheduleOwnerEntity);
}
