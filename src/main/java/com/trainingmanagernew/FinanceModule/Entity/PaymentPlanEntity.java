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

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal recurringAmount;

    @Column(nullable = false)
    private UUID customerId;

    @ManyToOne(optional = false)
    private PaymentOwnerEntity paymentOwnerEntity;

    private String description;
}
