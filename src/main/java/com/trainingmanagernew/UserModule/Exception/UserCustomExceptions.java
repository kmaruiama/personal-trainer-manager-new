package com.trainingmanagernew.UserModule.Exception;

public class UserCustomExceptions {
    public static class EmailAlreadyExistsException extends RuntimeException{
        public EmailAlreadyExistsException(){
            super("O email já foi cadastrado na plataforma.");
        }
    }

    public static class UsernameAlreadyExistsException extends RuntimeException{
        public UsernameAlreadyExistsException(){
            super("O username já foi cadastrado na plataforma");
        }
    }

    public static class PhoneNumberAlreadyExistsException extends RuntimeException{
        public PhoneNumberAlreadyExistsException(){ super("O número de telefone já foi cadastrado na plataforma"); }
    }

    public static class NoCompatibleObjectFoundInInterceptedMethodArgument extends RuntimeException{
        public NoCompatibleObjectFoundInInterceptedMethodArgument(){
            super("Nenhum objeto compativel encontrado para validar a autorização do método");
        }
    }

    public static class UnauthorizedRequest extends RuntimeException{
        public UnauthorizedRequest(){ super("Recurso não disponível para esse usuário"); }
    }

    public static class NoArgument extends RuntimeException{
        public NoArgument(){ super("Não há argumento para validar"); }
    }

    public static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(){ super("Usuário não encontrado"); }
    }


}
