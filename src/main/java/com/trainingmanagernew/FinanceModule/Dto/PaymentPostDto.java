package com.trainingmanagernew.FinanceModule.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

//anotar os validators do jakarta aki dpsss
public class PaymentPostDto {
    private UUID paymentOwnerEntityId;

    private BigDecimal payment;

    private String paymentMode;

    private UUID customerId;

    private LocalDate datePayment;

    private LocalDate dueDate;

    private UUID optionalPaymentEntityId;



    public UUID getOptionalPaymentEntityId() {
        return optionalPaymentEntityId;
    }

    public void setOptionalPaymentEntityId(UUID optionalPaymentEntityId) {
        this.optionalPaymentEntityId = optionalPaymentEntityId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public UUID getPaymentOwnerEntityId() {
        return paymentOwnerEntityId;
    }

    public void setPaymentOwnerEntityId(UUID paymentOwnerEntityId) {
        this.paymentOwnerEntityId = paymentOwnerEntityId;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public LocalDate getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(LocalDate datePayment) {
        this.datePayment = datePayment;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
