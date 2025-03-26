package com.trainingmanagernew.UserModule.Service.Delete;

import com.trainingmanagernew.UserModule.Repository.UserRepository;
import com.trainingmanagernew.UserModule.UserEventEmitter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserService {
    private final UserRepository userRepository;
    private final UserEventEmitter userEventEmitter;

    DeleteUserService(UserRepository userRepository, UserEventEmitter userEventEmitter){
        this.userRepository = userRepository;
        this.userEventEmitter = userEventEmitter;
    }
    public void delete(UUID uuid){
        userRepository.deleteById(uuid);
        userEventEmitter.userDeleted(uuid);
    }
}
