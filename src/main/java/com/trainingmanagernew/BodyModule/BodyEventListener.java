package com.trainingmanagernew.BodyModule;

import com.trainingmanagernew.BodyModule.Dto.BodyIdTransferDto;
import com.trainingmanagernew.BodyModule.Service.Register.RegisterBodyOwnerService;
import com.trainingmanagernew.BodyModule.Service.Register.RegisterCustomerBodyDtoConversion;
import com.trainingmanagernew.CustomerModule.Dto.CustomerIdTransferDto;
import com.trainingmanagernew.Shared.DataTypes.GenericEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class BodyEventListener {
    private final RegisterBodyOwnerService registerBodyOwnerService;
    private final RegisterCustomerBodyDtoConversion registerCustomerBodyDtoConversion;

    public BodyEventListener(RegisterBodyOwnerService registerBodyOwnerService, RegisterCustomerBodyDtoConversion registerCustomerBodyDtoConversion) {
        this.registerBodyOwnerService = registerBodyOwnerService;
        this.registerCustomerBodyDtoConversion = registerCustomerBodyDtoConversion;
    }

    @EventListener
    public void eventHandler(GenericEvent<?> event){
        switch (event.getEventType()){
            case "SUCCESSFUL-CUSTOMER-REGISTRATION":
                BodyIdTransferDto bodyIdTransferDto = registerCustomerBodyDtoConversion
                        .conversion((CustomerIdTransferDto) event.getEventData());
                registerBodyOwnerService.register(bodyIdTransferDto);
        }
    }
}
