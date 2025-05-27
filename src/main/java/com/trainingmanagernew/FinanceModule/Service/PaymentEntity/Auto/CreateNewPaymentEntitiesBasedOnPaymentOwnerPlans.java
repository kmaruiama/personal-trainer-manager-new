package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Auto;

import com.trainingmanagernew.FinanceModule.Entity.PaymentEntity;
import com.trainingmanagernew.FinanceModule.Entity.PaymentMethod;
import com.trainingmanagernew.FinanceModule.Entity.PaymentOwnerEntity;
import com.trainingmanagernew.FinanceModule.Entity.PaymentPlanEntity;
import com.trainingmanagernew.FinanceModule.Repository.PaymentEntityRepository;
import com.trainingmanagernew.FinanceModule.Repository.PaymentOwnerEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


//esse serviço apenas cria novos pagamentos
//acho que no futuro seria interessante dividir varias tabelas (uma pra cada tipo de plano)
//pq ler todos os n rows da tabela de planos todos os dias me parece ineficiente
@Service
public class CreateNewPaymentEntitiesBasedOnPaymentOwnerPlans {
    private final PaymentOwnerEntityRepository paymentOwnerEntityRepository;
    private final PaymentEntityRepository paymentEntityRepository;

    public CreateNewPaymentEntitiesBasedOnPaymentOwnerPlans(PaymentOwnerEntityRepository paymentOwnerEntityRepository,
                                                            PaymentEntityRepository paymentEntityRepository) {
        this.paymentOwnerEntityRepository = paymentOwnerEntityRepository;
        this.paymentEntityRepository = paymentEntityRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * *")
    public void create () {
        List<PaymentOwnerEntity> paymentOwnerEntityList = paymentOwnerEntityRepository.findAllWithPlans();
        for (int i = 0; i < paymentOwnerEntityList.size(); i++){
            PaymentOwnerEntity paymentOwnerEntity = paymentOwnerEntityList.get(i);
            processPlans(paymentOwnerEntity);
        }
    }

    private void processPlans (PaymentOwnerEntity paymentOwnerEntity){
        List<PaymentPlanEntity> paymentPlanEntityList = paymentOwnerEntity.getPaymentPlanEntityList();
        for (int i = 0; i < paymentPlanEntityList.size(); i++){
            processPayment(paymentPlanEntityList.get(i));
        }
    }

    private void processPayment(PaymentPlanEntity paymentPlanEntity){
        PaymentMethod paymentMethod = paymentPlanEntity.getPaymentMethod();
        PaymentEntity paymentEntity = null;

        switch (paymentMethod) {
            case WEEKLY -> {
                if (paymentPlanEntity.getPaymentDay() == LocalDate.now().getDayOfWeek().getValue()) {
                    paymentEntity = new PaymentEntity();
                    LocalDate dueDate = LocalDate.now().plusDays(7);
                    paymentEntity.setDueDate(dueDate);
                    paymentEntity.setDescription(
                            "Pagamento referente à semana do dia " +
                                    dueDate.minusDays(7) +
                                    " até o dia " +
                                    dueDate +
                                    "."
                    );
                }
            }
            case MONTHLY -> {
                int paymentDay = paymentPlanEntity.getPaymentDay();
                LocalDate today = LocalDate.now();
                int todayDay = today.getDayOfMonth();
                int lastDayOfMonth = today.lengthOfMonth();

                boolean isTodayPaymentDay = (paymentDay == todayDay); // hoje é o dia de pagamento
                boolean isTodayLastDay = (todayDay == lastDayOfMonth); // hoje é o último dia do mês
                boolean isPaymentDayBeyondMonth = paymentDay > lastDayOfMonth; // dia do pagamento passa do span do mes
                                                                               // ex: dia de pagamento = 31 mas o mes só tem 30 dias

                boolean isPaymentDayAdjusted = isPaymentDayBeyondMonth && isTodayLastDay;
                //se o dia do pagamento é 31 e hoje é 30, e 30 é o ultimo dia e todos os casos semelhantes

                if (isTodayPaymentDay || isPaymentDayAdjusted) {
                    paymentEntity = new PaymentEntity();

                    LocalDate nextMonth = today.plusMonths(1);
                    int lengthOfNextMonth = nextMonth.lengthOfMonth();
                    //se vence no dia 31 e o mês só tem 28 dias o vencimento é no dia 28
                    int dayToPay = Math.min(paymentDay, lengthOfNextMonth);
                    LocalDate dueDate = nextMonth.withDayOfMonth(dayToPay);

                    paymentEntity.setDueDate(dueDate);


                    LocalDate startPeriod = dueDate.minusMonths(1);
                    int lengthOfStartPeriod = startPeriod.lengthOfMonth();
                    int startDay = Math.min(paymentDay, lengthOfStartPeriod);
                    startPeriod = startPeriod.withDayOfMonth(startDay);

                    paymentEntity.setDescription(
                            "Pagamento referente ao mês do dia " +
                                    startPeriod +
                                    " até o dia " +
                                    dueDate +
                                    "."
                    );
                }
            }
            case YEARLY -> {
                if (LocalDate.now().getDayOfYear() == paymentPlanEntity.getPaymentDay()) {
                    paymentEntity = new PaymentEntity();
                    LocalDate dueDate = LocalDate.now().plusYears(1);
                    paymentEntity.setDueDate(dueDate);
                    paymentEntity.setDescription(
                            "Pagamento referente ao ano do dia " +
                                    dueDate.minusYears(1) +
                                    " até o dia " +
                                    dueDate +
                                    "."
                    );
                }
            }

            case CUSTOM -> {
                long days = ChronoUnit.DAYS.between(paymentPlanEntity.getStartDate(),LocalDate.now());
                if (days % paymentPlanEntity.getCustomIntervalOfDays() == 0){
                    paymentEntity = new PaymentEntity();
                    LocalDate dueDate = LocalDate.now().plusDays(paymentPlanEntity.getCustomIntervalOfDays());
                    paymentEntity.setDueDate(dueDate);
                    paymentEntity.setDescription(
                            "Pagamento referente ao intervalo do dia " +
                                    dueDate.minusDays(paymentPlanEntity.getCustomIntervalOfDays()) +
                                    " até o dia " +
                                    dueDate +
                                    "."
                    );
                }
            }
        }

        if (paymentEntity != null) {
            paymentEntity.setPayed(false);
            paymentEntity.setAmount(paymentPlanEntity.getRecurringAmount());
            paymentEntity.setPaymentPlanEntity(paymentPlanEntity);
            paymentEntity.setCustomerId(paymentPlanEntity.getCustomerId());
            paymentEntity.setPaymentOwnerEntity(paymentPlanEntity.getPaymentOwnerEntity());
            paymentEntityRepository.save(paymentEntity);
        }
    }
}
