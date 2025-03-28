package com.trainingmanagernew.SecurityModule.Exception;

public class SecurityCustomExceptions {
    public static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(){
            super("Nenhum usuário com este nome encontrado");
        }
    }

    public static class RoleAtributionException extends RuntimeException{
        public RoleAtributionException() { super ("Tipo de autorização não encontrada"); }
    }

    public static class InvalidUserCredentialsException extends RuntimeException{
        public InvalidUserCredentialsException(){ super ("Senha ou usuário invalido(s)"); }
    }
}
