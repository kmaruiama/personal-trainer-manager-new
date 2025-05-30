package com.trainingmanagernew.FinanceModule.Service.PaymentEntity.Post;

import com.trainingmanagernew.FinanceModule.Dto.PaymentEntity.PaymentPostDto;
import com.trainingmanagernew.FinanceModule.Entity.PaymentEntity;
import com.trainingmanagernew.FinanceModule.Entity.PaymentOwnerEntity;
import com.trainingmanagernew.FinanceModule.Exception.FinanceCustomExceptions;
import com.trainingmanagernew.FinanceModule.Repository.PaymentEntityRepository;
import com.trainingmanagernew.FinanceModule.Repository.PaymentOwnerEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

//nao vou deixar ele adicionar pagamentos do planejamento, isso é funcao do servidor.
//aqui é só diarias e avulsos
@Service
public class AddNewPaymentEntityServiceImpl implements AddNewPaymentEntityService{
    private final PaymentOwnerEntityRepository paymentOwnerEntityRepository;
    private final PaymentEntityRepository paymentEntityRepository;

    public AddNewPaymentEntityServiceImpl(PaymentOwnerEntityRepository paymentOwnerEntityRepository, PaymentEntityRepository paymentEntityRepository) {
        this.paymentOwnerEntityRepository = paymentOwnerEntityRepository;
        this.paymentEntityRepository = paymentEntityRepository;
    }

    @Override
    @Transactional
    public void add(PaymentPostDto paymentPostDto) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaymentDate(paymentPostDto.getPaymentDate());
        paymentEntity.setAmount(paymentPostDto.getAmount());
        paymentEntity.setPayed(paymentPostDto.isPayed());
        if (paymentPostDto.getCustomerId() != null) {
            paymentEntity.setCustomerId(paymentPostDto.getCustomerId());
        }
        if (paymentPostDto.getDescription() != null) {
            paymentEntity.setDescription(paymentPostDto.getDescription());
        }
        if (!paymentPostDto.isPayed()){
            paymentEntity.setDueDate(paymentPostDto.getDueDate());
        }
        setPaymentOwnerEntity(paymentEntity, paymentPostDto.getPaymentOwnerEntityId());
        checkInconsistencies(paymentEntity);
        paymentEntityRepository.save(paymentEntity);
    }

    private void setPaymentOwnerEntity(PaymentEntity paymentEntity, UUID paymentOwnerEntityId){
        Optional<PaymentOwnerEntity> paymentOwnerEntityOptional = paymentOwnerEntityRepository.findById(paymentOwnerEntityId);
        if (paymentOwnerEntityOptional.isPresent()){
            paymentEntity.setPaymentOwnerEntity(paymentOwnerEntityOptional.get());
        }
        else throw new FinanceCustomExceptions.FinanceOwnerEntityNotFoundException();
    }

    private void checkInconsistencies(PaymentEntity paymentEntity){
        if (!paymentEntity.isPayed() && paymentEntity.getDueDate() == null){
            throw new FinanceCustomExceptions.IrregularPaymentPostDtoException();
        }
    }
}
