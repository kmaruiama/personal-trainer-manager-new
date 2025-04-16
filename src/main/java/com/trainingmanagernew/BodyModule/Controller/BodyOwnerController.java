package com.trainingmanagernew.BodyModule.Controller;

import com.trainingmanagernew.BodyModule.Service.BodyOwnerEntityService.Get.GetBodyOwnerIdFromCustomerId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/body/owner")
public class BodyOwnerController {
    private final GetBodyOwnerIdFromCustomerId getBodyOwnerIdFromCustomerId;

    public BodyOwnerController(GetBodyOwnerIdFromCustomerId getBodyOwnerIdFromCustomerId) {
        this.getBodyOwnerIdFromCustomerId = getBodyOwnerIdFromCustomerId;
    }

    @GetMapping("/get")
    public ResponseEntity<UUID> getBodyOwnerIdFromCustomerId(UUID customerId){
        UUID id = getBodyOwnerIdFromCustomerId.get(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}
