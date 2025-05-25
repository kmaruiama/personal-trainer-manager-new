package com.trainingmanagernew.FinanceModule.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class PaymentPlanEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMode;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private Integer paymentDay;

    private LocalDate endDate;

    private Integer customIntervalOfDays;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal recurringAmount;

    @Column(nullable = false)
    private UUID customerId;

    @ManyToOne(optional = false)
    private PaymentOwnerEntity paymentOwnerEntity;

    private String description;

    public UUID getId() {
        return id;
    }

    public PaymentMethod getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMethod paymentMode) {
        this.paymentMode = paymentMode;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(Integer paymentDay) {
        this.paymentDay = paymentDay;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getRecurringAmount() {
        return recurringAmount;
    }

    public void setRecurringAmount(BigDecimal recurringAmount) {
        this.recurringAmount = recurringAmount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCustomIntervalOfDays() {
        return customIntervalOfDays;
    }

    public void setCustomIntervalOfDays(Integer customIntervalOfDays) {
        this.customIntervalOfDays = customIntervalOfDays;
    }
}
