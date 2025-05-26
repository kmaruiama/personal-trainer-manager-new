package com.trainingmanagernew.FinanceModule.Service.PaymentPlanEntity.Post;

import com.trainingmanagernew.FinanceModule.Dto.PaymentPlanPostDto;
import com.trainingmanagernew.FinanceModule.Entity.PaymentMethod;
import com.trainingmanagernew.FinanceModule.Entity.PaymentOwnerEntity;
import com.trainingmanagernew.FinanceModule.Entity.PaymentPlanEntity;
import com.trainingmanagernew.FinanceModule.Exception.FinanceCustomExceptions;
import com.trainingmanagernew.FinanceModule.Repository.PaymentOwnerEntityRepository;
import com.trainingmanagernew.FinanceModule.Repository.PaymentPlanEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddNewPaymentPlanServiceImpl implements AddNewPaymentPlanService {
    private final PaymentOwnerEntityRepository paymentOwnerEntityRepository;
    private final PaymentPlanEntityRepository paymentPlanEntityRepository;

    public AddNewPaymentPlanServiceImpl(PaymentOwnerEntityRepository paymentOwnerEntityRepository, PaymentPlanEntityRepository paymentPlanEntityRepository) {
        this.paymentOwnerEntityRepository = paymentOwnerEntityRepository;
        this.paymentPlanEntityRepository = paymentPlanEntityRepository;
    }

    @Transactional
    @Override
    public void add(PaymentPlanPostDto paymentPlanPostDto) {
        PaymentPlanEntity paymentPlanEntity = new PaymentPlanEntity();

        //obrigatórios
        check29Feb(paymentPlanPostDto);

        paymentPlanEntity.setStartDate(paymentPlanPostDto.getStartDate());
        paymentPlanEntity.setRecurringAmount(paymentPlanPostDto.getRecurringAmount());
        //opcionais
        if (paymentPlanPostDto.getEndDate() != null){
            paymentPlanEntity.setEndDate(paymentPlanPostDto.getEndDate());
        }
        if (paymentPlanPostDto.getDescription() != null){
            paymentPlanEntity.setDescription(paymentPlanEntity.getDescription());
        }

        setPaymentOwnerEntity(paymentPlanEntity, paymentPlanPostDto.getPaymentOwnerEntityId());
        setPaymentMethod(paymentPlanEntity, paymentPlanPostDto.getPaymentMode());

        //se custom, a data da cobrança será sempre a data de inicio + intervalo de dias escolhido.
        //ficaria complexo demais e acredito que inútil pra 99.9% dos casos deixar escolher o dia nesse modo
        if (paymentPlanEntity.getPaymentMethod().equals(PaymentMethod.CUSTOM)){
            paymentPlanEntity.setCustomIntervalOfDays(paymentPlanPostDto.getCustomIntervalOfDays());
        }

        checkInconsistencies(paymentPlanEntity);
        paymentPlanEntityRepository.save(paymentPlanEntity);
    }

    private void setPaymentOwnerEntity(PaymentPlanEntity paymentPlanEntity, UUID id){
        Optional<PaymentOwnerEntity> paymentOwnerEntityOptional = paymentOwnerEntityRepository.findById(id);
        if (paymentOwnerEntityOptional.isPresent()){
            paymentPlanEntity.setPaymentOwnerEntity(paymentOwnerEntityOptional.get());
        }
        else {
            throw new FinanceCustomExceptions.FinanceOwnerEntityNotFoundException();
        }
    }

    private void setPaymentMethod(PaymentPlanEntity paymentPlanEntity, String paymentMethod){
        switch (paymentMethod){
            case "WEEKLY":
                paymentPlanEntity.setPaymentMethod(PaymentMethod.WEEKLY);
                break;
            case "MONTHLY":
                paymentPlanEntity.setPaymentMethod(PaymentMethod.MONTHLY);
                break;
            case "YEARLY":
                paymentPlanEntity.setPaymentMethod(PaymentMethod.YEARLY);
                break;
            case "CUSTOM":
                paymentPlanEntity.setPaymentMethod(PaymentMethod.CUSTOM);
                break;
        }
    }

    private void checkInconsistencies(PaymentPlanEntity paymentPlanEntity) {
        PaymentMethod method = paymentPlanEntity.getPaymentMethod();
        Integer paymentDay = paymentPlanEntity.getPaymentDay();

        if (paymentDay != null && !(method == PaymentMethod.WEEKLY || method == PaymentMethod.MONTHLY)) {
            throw new FinanceCustomExceptions.IrregularPaymentPlanPostDtoException();
        }
        if (method == PaymentMethod.WEEKLY && paymentDay != null && paymentDay > 7) {
            throw new FinanceCustomExceptions.IrregularPaymentPlanPostDtoException();
        }
    }
    private void check29Feb(PaymentPlanPostDto paymentPlanPostDto) {
        if (paymentPlanPostDto.getStartDate().getMonth().getValue() == 2
        && paymentPlanPostDto.getStartDate().getDayOfMonth() == 29) {
            paymentPlanPostDto.setStartDate(paymentPlanPostDto.getStartDate().plusDays(1));
        }
    }
}
