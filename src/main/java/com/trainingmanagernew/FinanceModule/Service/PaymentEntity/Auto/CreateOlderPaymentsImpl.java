package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Auto;

import com.trainingmanagernew.FinanceModule.Entity.PaymentEntity;
import com.trainingmanagernew.FinanceModule.Entity.PaymentPlanEntity;
import com.trainingmanagernew.FinanceModule.Repository.PaymentEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateOlderPaymentsImpl implements CreateOlderPayments{
    private final PaymentEntityRepository paymentEntityRepository;

    public CreateOlderPaymentsImpl(PaymentEntityRepository paymentEntityRepository) {
        this.paymentEntityRepository = paymentEntityRepository;
    }

    @Transactional
    public void create(PaymentPlanEntity paymentPlanEntity) {
        List<LocalDate> paymentDates = new ArrayList<>();
        switch (paymentPlanEntity.getPaymentMethod()){
            case WEEKLY -> {
                weeklyMode(paymentPlanEntity, paymentDates);
            }
            case MONTHLY -> {
                monthlyMode(paymentPlanEntity, paymentDates);
            }
            case YEARLY -> {
                yearlyMode(paymentPlanEntity, paymentDates);
            }
            case CUSTOM -> {
                customMode(paymentPlanEntity, paymentDates);
            }
        }
        List<PaymentEntity> paymentEntityList = transformDatesIntoEntities(paymentDates, paymentPlanEntity);
        paymentEntityRepository.saveAll(paymentEntityList);
    }

    void weeklyMode(PaymentPlanEntity paymentPlanEntity, List<LocalDate> paymentDates) {
        LocalDate startDate = paymentPlanEntity.getStartDate();
        int startDay = startDate.getDayOfWeek().getValue();
        int paymentDay = paymentPlanEntity.getPaymentDay();

        int desloc = (paymentDay - startDay + 7) % 7;

        LocalDate firstPayment = startDate.plusDays(desloc);
        while (firstPayment.isBefore(LocalDate.now())){
            paymentDates.add(firstPayment);
            firstPayment = firstPayment.plusDays(7);
        }
    }

    void monthlyMode(PaymentPlanEntity paymentPlanEntity, List<LocalDate> paymentDates) {
        LocalDate startDate = paymentPlanEntity.getStartDate();
        int paymentDay = paymentPlanEntity.getPaymentDay();

        LocalDate current = startDate;

        if (startDate.getDayOfMonth() > paymentDay) {
            current = current.plusMonths(1);
        }

        while (current.isBefore(LocalDate.now())) {
            int lengthOfMonth = current.lengthOfMonth();

            //se o cliente escolher dia 30 pra receber, ele sempre vai recebr dia 30 exceto nos meses
            //que o dia 30 nao existe (fevereiro)
            int dayToPay = Math.min(paymentDay, lengthOfMonth);

            LocalDate paymentDate = current.withDayOfMonth(dayToPay);
            paymentDates.add(paymentDate);

            //vai pro proximo mes e começa a conta dnv
            current = current.plusMonths(1);
        }
    }

    void yearlyMode(PaymentPlanEntity paymentPlanEntity, List<LocalDate> dateList){
        LocalDate startDate = paymentPlanEntity.getStartDate();
        int paymentDay = paymentPlanEntity.getPaymentDay();

        while(startDate.isBefore(LocalDate.now())){
            LocalDate paymentDate = startDate.withDayOfYear(paymentDay);
            dateList.add(paymentDate);
            startDate = startDate.plusYears(1L);
        }
    }

    void customMode(PaymentPlanEntity paymentPlanEntity, List<LocalDate> dateList){
        LocalDate startDate = paymentPlanEntity.getStartDate();

        while(startDate.isBefore(LocalDate.now())){
            dateList.add(startDate);
            startDate = startDate.plusDays(paymentPlanEntity.getCustomIntervalOfDays());
        }
    }

    List<PaymentEntity> transformDatesIntoEntities(List<LocalDate> dateList, PaymentPlanEntity paymentPlanEntity){
        List<PaymentEntity> paymentEntityList = new ArrayList<>();
        for (int i = 0; i<dateList.size(); i++){
            PaymentEntity paymentEntity = new PaymentEntity();
            paymentEntity.setPaymentDate(dateList.get(i));
            paymentEntity.setDueDate(dateList.get(i));
            paymentEntity.setPayed(true);
            paymentEntity.setPaymentPlanEntity(paymentPlanEntity);
            paymentEntity.setCustomerId(paymentPlanEntity.getCustomerId());
            paymentEntity.setAmount(paymentPlanEntity.getRecurringAmount());
            paymentEntity.setDescription("Pagamento antigo, anterior à da criação do plano financeiro do cliente");
            paymentEntity.setPaymentOwnerEntity(paymentPlanEntity.getPaymentOwnerEntity());
            paymentEntityList.add(paymentEntity);
        }
        return paymentEntityList;
    }



}
