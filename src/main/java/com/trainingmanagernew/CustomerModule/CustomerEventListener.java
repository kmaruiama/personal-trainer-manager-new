package com.trainingmanagernew.CustomerModule;

import com.trainingmanagernew.CustomerModule.Service.DeleteCustomerOwnerService;
import com.trainingmanagernew.CustomerModule.Service.RegisterCustomerOwnerService;
import com.trainingmanagernew.Shared.DataTypes.GenericEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerEventListener {

    private final RegisterCustomerOwnerService registerCustomerOwnerService;
    private final DeleteCustomerOwnerService deleteCustomerOwnerService;

    public CustomerEventListener(RegisterCustomerOwnerService registerCustomerOwnerService, DeleteCustomerOwnerService deleteCustomerOwnerService) {
        this.registerCustomerOwnerService = registerCustomerOwnerService;
        this.deleteCustomerOwnerService = deleteCustomerOwnerService;
    }

    @EventListener
    public void eventHandler(GenericEvent<?> event) {
        switch (event.getEventType()) {
            case "USER-REGISTERED-COMPLETE":
                registerCustomerOwnerService.register(event.getUserId());
                break;
            case "OBLITERATE":
                deleteCustomerOwnerService.delete(event.getUserId());
                break;
        }
    }
}
