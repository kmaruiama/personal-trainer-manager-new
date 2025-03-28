package com.trainingmanagernew.UserModule;

import com.trainingmanagernew.Shared.DataTypes.GenericEvent;
import com.trainingmanagernew.UserModule.Service.Delete.DeleteUserService;
import com.trainingmanagernew.UserModule.Service.Delete.RollbackDeleteUserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
    private final DeleteUserService deleteUserService;
    private final RollbackDeleteUserService rollbackDeleteUserService;
    private final UserEventEmitter userEventEmitter;

    public UserEventListener(DeleteUserService deleteUserService, RollbackDeleteUserService rollbackDeleteUserService, UserEventEmitter userEventEmitter) {
        this.deleteUserService = deleteUserService;
        this.rollbackDeleteUserService = rollbackDeleteUserService;
        this.userEventEmitter = userEventEmitter;
    }

    @EventListener
    public void eventHandler(GenericEvent<?> event){
        switch (event.getEventType()){
            //rollback manual
            case "UNSUCCESSFUL-TRAINER-REGISTRATION":
                rollbackDeleteUserService.delete(event.getUserId());
                break;
            case "SUCCESSFUL-TRAINER-REGISTRATION":
                userEventEmitter.sucessfulRegistration(event.getUserId());
        }
    }
}
