package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Auto;

import com.trainingmanagernew.FinanceModule.Entity.PaymentEntity;
import com.trainingmanagernew.FinanceModule.Entity.PaymentMethod;
import com.trainingmanagernew.FinanceModule.Entity.PaymentOwnerEntity;
import com.trainingmanagernew.FinanceModule.Entity.PaymentPlanEntity;
import com.trainingmanagernew.FinanceModule.Repository.PaymentEntityRepository;
import com.trainingmanagernew.FinanceModule.Repository.PaymentOwnerEntityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


//esse serviço apenas cria novos pagamentos
@Service
public class CreateNewPaymentEntitiesBasedOnPaymentOwnerPlans {
    private final PaymentOwnerEntityRepository paymentOwnerEntityRepository;
    private final PaymentEntityRepository paymentEntityRepository;

    public CreateNewPaymentEntitiesBasedOnPaymentOwnerPlans(PaymentOwnerEntityRepository paymentOwnerEntityRepository,
                                                            PaymentEntityRepository paymentEntityRepository) {
        this.paymentOwnerEntityRepository = paymentOwnerEntityRepository;
        this.paymentEntityRepository = paymentEntityRepository;
    }

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
                if (paymentPlanEntity.getPaymentDay() == LocalDate.now().getDayOfMonth()) {
                    paymentEntity = new PaymentEntity();
                    LocalDate dueDate = LocalDate.now().plusMonths(1);
                    paymentEntity.setDueDate(dueDate);
                    paymentEntity.setDescription(
                            "Pagamento referente ao mês do dia " +
                                    dueDate.minusMonths(1) +
                                    " até o dia " +
                                    dueDate +
                                    "."
                    );
                }
            }
            case YEARLY -> {
                LocalDate hoje = LocalDate.now();
                LocalDate comparado = paymentPlanEntity.getStartDate();
                if (hoje.getMonth() == comparado.getMonth() && hoje.getDayOfMonth() == comparado.getDayOfMonth()) {
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
            paymentEntity = paymentEntityRepository.save(paymentEntity);
        }
    }
}
