package com.trainingmanagernew.FinanceModule.Repository;

import com.trainingmanagernew.FinanceModule.Entity.PaymentOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentOwnerEntityRepository extends JpaRepository<PaymentOwnerEntity, UUID> {
}
