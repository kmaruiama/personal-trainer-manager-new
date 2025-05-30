package com.trainingmanagernew.FinanceModule.Dto.PaymentEntity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class PaymentPostDto {
    private UUID optionalPaymentEntityId;

    @NotNull
    @Min(0)
    private BigDecimal amount;

    @NotNull
    private LocalDate paymentDate;

    @NotNull
    private boolean payed;

    //se nao foi pago, adicionar duedate para lembrar ao profissional dps
    private LocalDate dueDate;

    @NotBlank
    private String description;

    //pode ser null pq imagino que pagamentos avulsos vao ser usados majoritariamente para
    //clientes nao cadastrados
    private UUID customerId;

    @NotNull
    private UUID paymentOwnerEntityId;

    public UUID getOptionalPaymentEntityId() {
        return optionalPaymentEntityId;
    }

    public void setOptionalPaymentEntityId(UUID optionalPaymentEntityId) {
        this.optionalPaymentEntityId = optionalPaymentEntityId;
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

    public UUID getPaymentOwnerEntityId() {
        return paymentOwnerEntityId;
    }

    public void setPaymentOwnerEntityId(UUID paymentOwnerEntityId) {
        this.paymentOwnerEntityId = paymentOwnerEntityId;
    }
}
