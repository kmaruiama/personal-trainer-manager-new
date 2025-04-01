package com.trainingmanagernew.BodyModule.Exception;

public class BodyCustomExceptions {
    public static class BodyOwnerEntityNotFound extends RuntimeException{
        public BodyOwnerEntityNotFound(){ super ("Erro ao encontrar o cliente com este id."); }
    }
    public static class BodyEntityListIsEmpty extends RuntimeException{
        public BodyEntityListIsEmpty(){ super ("O cliente não possui composição corporal cadastrada"); }
    }
    public static class BodyEntityNotFound extends RuntimeException{
        public BodyEntityNotFound(){ super ("Erro ao encontrar registro de composição corporal com este id"); }
    }

    public static class UnauthorizedRequest extends RuntimeException{
        public UnauthorizedRequest(){ super("Recurso não disponível para esse usuário"); }
    }

    public static class NoCompatibleObjectFoundInInterceptedMethodArgument extends RuntimeException{
        public NoCompatibleObjectFoundInInterceptedMethodArgument(){
            super("Nenhum objeto compativel encontrado para validar a autorização do método");
        }
    }

}
