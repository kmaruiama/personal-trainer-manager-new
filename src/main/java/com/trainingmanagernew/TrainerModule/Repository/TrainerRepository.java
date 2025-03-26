package com.trainingmanagernew.TrainerModule.Repository;

import com.trainingmanagernew.TrainerModule.Entity.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrainerRepository extends JpaRepository<TrainerEntity, Long> {
    boolean existsByCpf(String cpf);

    void deleteByOwnerId(UUID ownerId);
}
