package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Auto;

import com.trainingmanagernew.FinanceModule.Entity.PaymentPlanEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateOlderPayments {
    public void create(PaymentPlanEntity paymentPlanEntity) {
        List<LocalDate> paymentDates = new ArrayList<>();
        switch (paymentPlanEntity.getPaymentMethod()){
            case WEEKLY -> {
                weeklyMode(paymentPlanEntity, paymentDates);
            }
            case MONTHLY -> {
                monthlyMode(paymentPlanEntity, paymentDates);
            }
        }

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




}
