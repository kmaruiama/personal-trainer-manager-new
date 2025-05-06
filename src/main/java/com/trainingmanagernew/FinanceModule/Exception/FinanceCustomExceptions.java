package com.trainingmanagernew.FinanceModule.Exception;

public class FinanceCustomExceptions {
    public static class FinanceOwnerEntityNotFoundException extends RuntimeException {
        public FinanceOwnerEntityNotFoundException () {super("Entidade dona dos pagamentos não encontrada");}
    }
}
