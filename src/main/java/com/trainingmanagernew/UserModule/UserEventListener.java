package com.trainingmanagernew.UserModule;

import com.trainingmanagernew.SharedDataTypes.GenericEvent;
import com.trainingmanagernew.UserModule.Service.Delete.DeleteUserService;
import com.trainingmanagernew.UserModule.Service.Delete.RollbackDeleteUserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
    private final DeleteUserService deleteUserService;
    private final RollbackDeleteUserService rollbackDeleteUserService;

    public UserEventListener(DeleteUserService deleteUserService, RollbackDeleteUserService rollbackDeleteUserService) {
        this.deleteUserService = deleteUserService;
        this.rollbackDeleteUserService = rollbackDeleteUserService;
    }

    @EventListener
    public void eventHandler(GenericEvent<?> event){
        switch (event.getEventType()){
            //rollback manual
            case "UNSUCCESSFUL-TRAINER-REGISTRATION":
                rollbackDeleteUserService.delete(event.getUserId());
                break;
        }
    }
}
