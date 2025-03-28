package com.trainingmanagernew.CustomerModule.Repository;

import com.trainingmanagernew.CustomerModule.Entity.CustomerEntityOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerOwnerRepository extends JpaRepository<CustomerEntityOwner, UUID> {
    void deleteByOwnerId(UUID ownerId);
}
