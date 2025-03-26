package com.trainingmanagernew.UserModule.Service.Delete;

import com.trainingmanagernew.UserModule.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RollbackDeleteUserService {
    private final UserRepository userRepository;
    RollbackDeleteUserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void delete(UUID uuid){
        userRepository.deleteById(uuid);
    }
}
