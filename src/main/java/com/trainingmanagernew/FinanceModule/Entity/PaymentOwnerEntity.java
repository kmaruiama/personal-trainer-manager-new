package com.trainingmanagernew.FinanceModule.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class PaymentOwnerEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;

    @OneToMany
    private List<PaymentEntity> paymentEntityList = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<PaymentEntity> getPaymentEntityList() {
        return paymentEntityList;
    }

    public void setPaymentEntityList(List<PaymentEntity> paymentEntityList) {
        this.paymentEntityList = paymentEntityList;
    }
}
