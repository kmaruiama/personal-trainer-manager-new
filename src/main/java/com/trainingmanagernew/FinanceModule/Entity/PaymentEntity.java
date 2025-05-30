package com.trainingmanagernew.FinanceModule.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private PaymentPlanEntity paymentPlanEntity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate dueDate;

    //data que foi pago de fato
    private LocalDate paymentDate;

    @Column(nullable = false)
    private boolean payed;

    private String description;

    //aqui por possíveis pagamentos avulsos
    @Column(nullable = false)
    private UUID customerId;

    //aqui por possíveis pagamentos avulsos
    @ManyToOne(optional = false)
    private PaymentOwnerEntity paymentOwnerEntity;

    public UUID getId() {
        return id;
    }

    public PaymentPlanEntity getPaymentPlanEntity() {
        return paymentPlanEntity;
    }

    public void setPaymentPlanEntity(PaymentPlanEntity paymentPlanEntity) {
        this.paymentPlanEntity = paymentPlanEntity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public PaymentOwnerEntity getPaymentOwnerEntity() {
        return paymentOwnerEntity;
    }

    public void setPaymentOwnerEntity(PaymentOwnerEntity paymentOwnerEntity) {
        this.paymentOwnerEntity = paymentOwnerEntity;
    }
}