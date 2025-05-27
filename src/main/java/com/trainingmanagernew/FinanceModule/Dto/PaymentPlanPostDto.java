package com.trainingmanagernew.FinanceModule.Dto;

import com.trainingmanagernew.FinanceModule.Entity.PaymentMethod;
import com.trainingmanagernew.FinanceModule.Entity.PaymentOwnerEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

//anotar os validators do jakarta aki dpsss
public class PaymentPlanPostDto {
    private UUID optionalPaymentPlanEntityId;

    @NotNull
    @NotBlank
    private String paymentMode;

    @NotNull
    private LocalDate startDate;

    @NotNull
    @Min(1)
    @Max(365)
    private Integer paymentDay;

    @NotNull
    private LocalDate endDate;

    @Min(0)
    @NotNull
    private BigDecimal recurringAmount;

    @NotNull
    private UUID customerId;

    @NotNull
    private UUID paymentOwnerEntityId;

    private Integer customIntervalOfDays;

    private String description;

    private Boolean createOlderPayments;

    public UUID getOptionalPaymentPlanEntityId() {
        return optionalPaymentPlanEntityId;
    }

    public void setOptionalPaymentPlanEntityId(UUID optionalPaymentPlanEntityId) {
        this.optionalPaymentPlanEntityId = optionalPaymentPlanEntityId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
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

    public UUID getPaymentOwnerEntityId() {
        return paymentOwnerEntityId;
    }

    public void setPaymentOwnerEntityId(UUID paymentOwnerEntityId) {
        this.paymentOwnerEntityId = paymentOwnerEntityId;
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

    public Boolean getCreateOlderPayments() {
        return createOlderPayments;
    }

    public void setCreateOlderPayments(Boolean createOlderPayments) {
        this.createOlderPayments = createOlderPayments;
    }
}
