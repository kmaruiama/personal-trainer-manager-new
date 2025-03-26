package com.trainingmanagernew.Security.Exception;

public class SecurityCustomExceptions {
    public static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(){
            super("Nenhum usuário com este nome encontrado");
        }
    }
}
