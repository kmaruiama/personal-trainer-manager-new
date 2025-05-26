package com.trainingmanagernew.FinanceModule.Repository;

import com.trainingmanagernew.FinanceModule.Entity.PaymentOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentOwnerEntityRepository extends JpaRepository<PaymentOwnerEntity, UUID> {
    @Query("SELECT p from PaymentOwnerEntity p WHERE p.paymentPlanEntityList.size > 0")
    List<PaymentOwnerEntity> findAllWithPlans();
}
