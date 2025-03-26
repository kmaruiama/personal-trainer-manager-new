package com.trainingmanagernew.UserModule.Service.Register;

import com.trainingmanagernew.UserModule.Dto.RegisterDto;
import com.trainingmanagernew.UserModule.Entity.UserEntity;
import com.trainingmanagernew.TrainerModule.Repository.TrainerRepository;
import com.trainingmanagernew.UserModule.Repository.UserRepository;
import com.trainingmanagernew.UserModule.UserEventEmitter;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewUserService {

    private final NewUserValidatorService newUserValidatorService;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final UserEventEmitter userEventEmitter;

    RegisterNewUserService(NewUserValidatorService newUserValidatorService,
                           UserRepository userRepository,
                           TrainerRepository trainerRepository, UserEventEmitter userEventEmitter){
        this.newUserValidatorService = newUserValidatorService;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.userEventEmitter = userEventEmitter;
    }

    public void register(RegisterDto registerDto){
        newUserValidatorService.validate(registerDto);
        UserEntity userEntity = createUserEntity(registerDto);
        userEventEmitter.newUserRegistered(userEntity.getId(), registerDto);
    }

    private UserEntity createUserEntity(RegisterDto registerDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setPassword(registerDto.getPassword());
        userEntity.setUsername(registerDto.getUsername());
        userRepository.save(userEntity);
        return userEntity;
    }

}
