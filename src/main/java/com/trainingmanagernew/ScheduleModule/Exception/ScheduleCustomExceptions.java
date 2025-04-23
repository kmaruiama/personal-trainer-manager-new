package com.trainingmanagernew.ScheduleModule.Exception;

public class ScheduleCustomExceptions {
    public static class ScheduleOwnerNotFoundException extends RuntimeException{
        public ScheduleOwnerNotFoundException(){ super("Usuário do módulo de agendas não encontrado"); }
    }
    public static class ScheduleConflictException extends RuntimeException{
        public ScheduleConflictException(){ super("Já existe um horário nesse dia na agenda"); }
    }

    public static class ScheduleEntityListIsEmptyException extends RuntimeException{
        public ScheduleEntityListIsEmptyException(){ super ("O treinador não possui horários cadastrados"); }
    }

    public static class ScheduleEntityNotFoundException extends RuntimeException {
        public ScheduleEntityNotFoundException() {super("Agendamento com esse id não encontrado"); }
    }
    public static class InvalidTimeException extends RuntimeException {
        public InvalidTimeException() {super("Agendamento com o tempo inválido"); }
    }
}
