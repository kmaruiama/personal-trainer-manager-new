package com.trainingmanagernew.FinanceModule.Repository;

import com.trainingmanagernew.FinanceModule.Entity.PaymentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, UUID> {
    List<PaymentEntity> findByCustomerId(UUID customerId, Pageable pageable);
}
