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

    public static class IrregularPaymentPlanPostDtoException extends RuntimeException{
        public IrregularPaymentPlanPostDtoException() {super("Forma de pagamento irregular");}
    }

    public static class NoCompatibleObjectFoundInInterceptedMethodArgument extends RuntimeException{
        public NoCompatibleObjectFoundInInterceptedMethodArgument(){
            super("Nenhum objeto compativel encontrado para validar a autorização do método");
        }
    }

    public static class TokenParsingExceptionAtFinanceModule extends RuntimeException{
        public TokenParsingExceptionAtFinanceModule(){ super ("Erro ao realizar o parsing do JWT"); }
    }

    public static class UnauthorizedRequest extends RuntimeException{
        public UnauthorizedRequest(){ super("Recurso não disponível para esse usuário"); }
    }

}
