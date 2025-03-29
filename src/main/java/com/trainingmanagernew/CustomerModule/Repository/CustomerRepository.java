package com.trainingmanagernew.CustomerModule.Repository;

import com.trainingmanagernew.CustomerModule.Entity.CustomerEntity;
import com.trainingmanagernew.CustomerModule.Entity.CustomerEntityOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    List<CustomerEntity> findAllByLocalOwnerId(CustomerEntityOwner localOwnerId);

    List<CustomerEntity> findAllByLocalOwner(CustomerEntityOwner localOwner);
}
