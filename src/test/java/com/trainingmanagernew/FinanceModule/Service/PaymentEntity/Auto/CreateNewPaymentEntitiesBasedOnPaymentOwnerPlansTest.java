package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Auto;

import com.trainingmanagernew.FinanceModule.Entity.PaymentEntity;
import com.trainingmanagernew.FinanceModule.Entity.PaymentMethod;
import com.trainingmanagernew.FinanceModule.Entity.PaymentOwnerEntity;
import com.trainingmanagernew.FinanceModule.Entity.PaymentPlanEntity;
import com.trainingmanagernew.FinanceModule.Repository.PaymentEntityRepository;
import com.trainingmanagernew.FinanceModule.Repository.PaymentOwnerEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateNewPaymentEntitiesBasedOnPaymentOwnerPlansTest {

    @Mock
    PaymentOwnerEntityRepository paymentOwnerEntityRepository;

    @Mock
    PaymentEntityRepository paymentEntityRepository;

    @InjectMocks
    CreateNewPaymentEntitiesBasedOnPaymentOwnerPlans createNewPaymentEntitiesBasedOnPaymentOwnerPlans;

    @Test
    void shouldCreateCorrectPaymentEntity_Week() {
        LocalDate fixedNow = LocalDate.of(2025, 5, 13);

        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
            mockedLocalDate.when(LocalDate::now)
                    .thenReturn(fixedNow);

            UUID randomCustomerEntityId = UUID.randomUUID();

            PaymentOwnerEntity paymentOwnerEntity = new PaymentOwnerEntity();
            PaymentPlanEntity paymentPlanEntity = new PaymentPlanEntity();
            paymentPlanEntity.setPaymentOwnerEntity(paymentOwnerEntity);
            paymentPlanEntity.setCustomerId(randomCustomerEntityId);
            paymentPlanEntity.setPaymentMethod(PaymentMethod.WEEKLY);
            paymentPlanEntity.setRecurringAmount(BigDecimal.valueOf(80.00));
            paymentPlanEntity.setStartDate(LocalDate.of(2025, 5, 3));
            paymentPlanEntity.setPaymentDay(2);

            paymentOwnerEntity.setPaymentPlanEntityList(List.of(paymentPlanEntity));

            when(paymentOwnerEntityRepository.findAllWithPlans())
                    .thenReturn(List.of(paymentOwnerEntity));

            //interceptar o argumento enviado a save e armazenar no captor
            ArgumentCaptor<PaymentEntity> captor = ArgumentCaptor.forClass(PaymentEntity.class);
            when(paymentEntityRepository.save(captor.capture()))
                    .thenAnswer(inv -> inv.getArgument(0));

            assertDoesNotThrow(() -> createNewPaymentEntitiesBasedOnPaymentOwnerPlans.create());

            PaymentEntity paymentEntity = captor.getValue();
            assertNotNull(paymentEntity);

            assertEquals(BigDecimal.valueOf(80.00), paymentEntity.getAmount());
            assertEquals(LocalDate.of(2025, 5, 20), paymentEntity.getDueDate());
            verify(paymentEntityRepository).save(any(PaymentEntity.class));
        }
    }

    @Test
    void shouldCreateCorrectPaymentEntity_Month(){
    LocalDate fixedNow = LocalDate.of(2025, 5, 17);

        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
        mockedLocalDate.when(LocalDate::now)
                .thenReturn(fixedNow);

        UUID randomCustomerEntityId = UUID.randomUUID();

        PaymentOwnerEntity paymentOwnerEntity = new PaymentOwnerEntity();
        PaymentPlanEntity paymentPlanEntity = new PaymentPlanEntity();
        paymentPlanEntity.setPaymentOwnerEntity(paymentOwnerEntity);
        paymentPlanEntity.setCustomerId(randomCustomerEntityId);
        paymentPlanEntity.setPaymentMethod(PaymentMethod.MONTHLY);
        paymentPlanEntity.setRecurringAmount(BigDecimal.valueOf(240.00));
        paymentPlanEntity.setStartDate(LocalDate.of(2025, 5, 3));
        paymentPlanEntity.setPaymentDay(17);

        paymentOwnerEntity.setPaymentPlanEntityList(List.of(paymentPlanEntity));

        when(paymentOwnerEntityRepository.findAllWithPlans())
                .thenReturn(List.of(paymentOwnerEntity));

        ArgumentCaptor<PaymentEntity> captor = ArgumentCaptor.forClass(PaymentEntity.class);
        when(paymentEntityRepository.save(captor.capture()))
                .thenAnswer(inv -> inv.getArgument(0));

        assertDoesNotThrow(() -> createNewPaymentEntitiesBasedOnPaymentOwnerPlans.create());

        PaymentEntity paymentEntity = captor.getValue();
        assertNotNull(paymentEntity);

        assertEquals(BigDecimal.valueOf(240.00), paymentEntity.getAmount());
        assertEquals(LocalDate.of(2025, 6, 17), paymentEntity.getDueDate());
        verify(paymentEntityRepository).save(any(PaymentEntity.class));
        }
    }

    @Test
    void shouldCreateCorrectPaymentEntity_Yearly(){
        LocalDate fixedNow = LocalDate.of(2025, 7, 30);

        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
            mockedLocalDate.when(LocalDate::now)
                    .thenReturn(fixedNow);

            UUID randomCustomerEntityId = UUID.randomUUID();

            PaymentOwnerEntity paymentOwnerEntity = new PaymentOwnerEntity();
            PaymentPlanEntity paymentPlanEntity = new PaymentPlanEntity();
            paymentPlanEntity.setPaymentOwnerEntity(paymentOwnerEntity);
            paymentPlanEntity.setCustomerId(randomCustomerEntityId);
            paymentPlanEntity.setPaymentMethod(PaymentMethod.YEARLY);
            paymentPlanEntity.setRecurringAmount(BigDecimal.valueOf(240.00));
            paymentPlanEntity.setStartDate(LocalDate.of(2025, 7, 30));
            paymentPlanEntity.setPaymentDay(null);

            paymentOwnerEntity.setPaymentPlanEntityList(List.of(paymentPlanEntity));

            when(paymentOwnerEntityRepository.findAllWithPlans())
                    .thenReturn(List.of(paymentOwnerEntity));

            ArgumentCaptor<PaymentEntity> captor = ArgumentCaptor.forClass(PaymentEntity.class);
            when(paymentEntityRepository.save(captor.capture()))
                    .thenAnswer(inv -> inv.getArgument(0));
            assertDoesNotThrow(() -> createNewPaymentEntitiesBasedOnPaymentOwnerPlans.create());

            PaymentEntity paymentEntity = captor.getValue();
            assertNotNull(paymentEntity);

            assertEquals(BigDecimal.valueOf(240.00), paymentEntity.getAmount());
            assertEquals(LocalDate.of(2026, 7, 30), paymentEntity.getDueDate());
            verify(paymentEntityRepository).save(any(PaymentEntity.class));
        }
    }
}
