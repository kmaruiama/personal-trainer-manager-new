package com.trainingmanagernew.TrainerModule.Exception;

public class TrainerCustomExceptions {
    public static class CpfAlreadyExistsException extends RuntimeException{
        public CpfAlreadyExistsException(){
            super("O cpf já foi cadastrado na plataforma.");
        }
    }
}
