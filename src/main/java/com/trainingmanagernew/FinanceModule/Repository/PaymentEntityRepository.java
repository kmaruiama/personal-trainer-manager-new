package com.trainingmanagernew.FinanceModule.Repository;

import com.trainingmanagernew.FinanceModule.Entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, UUID> {
}
