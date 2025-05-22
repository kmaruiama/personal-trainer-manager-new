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

    private LocalDate dueDate;

    private LocalDate paymentDate;

    @Column(nullable = false)
    private boolean payed;

    private String description;

    @Column(nullable = false)
    private UUID customerId;

    @ManyToOne(optional = false)
    private PaymentOwnerEntity paymentOwnerEntity;
}