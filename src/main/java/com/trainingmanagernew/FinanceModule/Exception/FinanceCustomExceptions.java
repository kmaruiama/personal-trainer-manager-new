package com.trainingmanagernew.FinanceModule.Exception;

public class FinanceCustomExceptions {
    public static class FinanceOwnerEntityNotFoundException extends RuntimeException {
        public FinanceOwnerEntityNotFoundException () {super("Entidade dona dos pagamentos não encontrada");}
    }

    public static class FinanceEntityNotFoundException extends RuntimeException {
        public FinanceEntityNotFoundException () {super("Entidade de pagamento não encontrada");}
    }

    public static class IrregularPaymentPostDtoException extends RuntimeException{
        public IrregularPaymentPostDtoException() {super("Forma de pagamento irregular");}
    }
}
