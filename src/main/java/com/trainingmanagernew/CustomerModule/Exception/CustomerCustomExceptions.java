package com.trainingmanagernew.CustomerModule.Exception;

public class CustomerCustomExceptions {
    public static class OwnerNotFoundException extends RuntimeException{
        public OwnerNotFoundException() { super ("Id local do treinador não encontrado"); }
    }

    public static class TokenParsingExceptionAtCustomerModule extends RuntimeException{
        public TokenParsingExceptionAtCustomerModule(){ super ("Erro ao realizar o parsing do JWT"); }
    }
}
