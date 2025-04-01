package com.trainingmanagernew.CustomerModule.Dto;

import java.util.UUID;

public class CustomerIdTransferDto {
    private UUID customerId;
    private UUID customerOwnerId;

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getCustomerOwnerId() {
        return customerOwnerId;
    }

    public void setCustomerOwnerId(UUID customerOwnerId) {
        this.customerOwnerId = customerOwnerId;
    }
}
