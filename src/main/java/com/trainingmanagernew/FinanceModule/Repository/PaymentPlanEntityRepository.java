package com.trainingmanagernew.FinanceModule.Repository;

import com.trainingmanagernew.FinanceModule.Entity.PaymentPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentPlanEntityRepository extends JpaRepository<PaymentPlanEntity, UUID> {

}
