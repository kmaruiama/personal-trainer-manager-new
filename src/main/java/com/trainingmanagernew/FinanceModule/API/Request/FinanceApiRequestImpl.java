package com.trainingmanagernew.FinanceModule.API.Request;

import com.trainingmanagernew.CustomerModule.API.CustomerApiServer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FinanceApiRequestImpl implements FinanceApiRequests {

    private final CustomerApiServer customerApiServer;

    public FinanceApiRequestImpl(CustomerApiServer customerApiServer) {
        this.customerApiServer = customerApiServer;
    }

    @Override
    public String getCustomerName(UUID customerId) {
        return customerApiServer.serveCustomerName(customerId);
    }


}
