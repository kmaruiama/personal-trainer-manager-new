package com.trainingmanagernew;

import com.trainingmanagernew.Security.Config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class TrainingManagerFinaleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainingManagerFinaleApplication.class, args);
    }

}
