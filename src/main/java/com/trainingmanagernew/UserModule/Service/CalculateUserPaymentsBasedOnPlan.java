package com.trainingmanagernew.UserModule.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class CalculateUserPaymentsBasedOnPlan {
    private static final Logger LOGGER = Logger.getLogger(CalculateUserPaymentsBasedOnPlan.class.getName());
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(fixedRate = 5000)
    public void ok(){
        LOGGER.info("Agora são {" + sdf.format(new Date()) + "}");
    }
}
