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
}
