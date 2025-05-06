package com.trainingmanagernew.FinanceModule.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private PaymentOwnerEntity paymentOwnerEntity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal payment;

    @Enumerated(EnumType.STRING)
    private PaymentMethod modalidade;

    private UUID customerId;

    private String customerName; //como isso vai virar um servico sozinho no futuro, nao considero wet

    private LocalDate datePayment;

    private LocalDate dueDate;

    private boolean payed;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PaymentOwnerEntity getPaymentOwnerEntity() {
        return paymentOwnerEntity;
    }

    public void setPaymentOwnerEntity(PaymentOwnerEntity paymentOwnerEntity) {
        this.paymentOwnerEntity = paymentOwnerEntity;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public PaymentMethod getModalidade() {
        return modalidade;
    }

    public void setModalidade(PaymentMethod modalidade) {
        this.modalidade = modalidade;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }
}
