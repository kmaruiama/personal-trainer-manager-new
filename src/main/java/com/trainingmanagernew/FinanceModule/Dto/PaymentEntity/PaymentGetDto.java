package com.trainingmanagernew.FinanceModule.Dto.PaymentEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class PaymentGetDto {
    private UUID id;
    private UUID paymentPlanEntityId;
    private BigDecimal amount;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private boolean payed;
    private String description;

    //externo ao módulo
    private String customerName;

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPaymentPlanEntityId() {
        return paymentPlanEntityId;
    }

    public void setPaymentPlanEntityId(UUID paymentPlanEntityId) {
        this.paymentPlanEntityId = paymentPlanEntityId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
