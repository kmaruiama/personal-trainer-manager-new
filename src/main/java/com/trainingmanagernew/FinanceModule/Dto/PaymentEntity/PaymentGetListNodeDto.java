package com.trainingmanagernew.FinanceModule.Dto.PaymentEntity;

import java.time.LocalDate;
import java.util.UUID;

//na interface de usuario, vai aparecer simplesmente
//o pagamento referente a x prazo e o seu id, serve pra nao
//ter que trazer a entidade inteira na hora de servir a lista de pagamentos
//de um plano cadastrado pelo cliente
public class PaymentGetListNodeDto {
    private UUID id;
    private LocalDate dueDate;
    private boolean payed;

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }
}
