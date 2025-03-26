package com.trainingmanagernew.UserModule.Service.Register;

import com.trainingmanagernew.UserModule.Dto.RegisterDto;
import com.trainingmanagernew.UserModule.Exception.UserCustomExceptions;
import com.trainingmanagernew.UserModule.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class NewUserValidatorService implements NewUserValidation {

    private final UserRepository userRepository;

    NewUserValidatorService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void checkEmailUnique(String email) {
        if (userRepository.existsByEmail(email)){
            throw new UserCustomExceptions.EmailAlreadyExistsException();
        }
    }

    @Override
    public void checkUsernameUnique(String username) {
        if (userRepository.existsByUsername(username)){
            throw new UserCustomExceptions.UsernameAlreadyExistsException();
        }
    }

    public void validate(RegisterDto registerDto){
        checkEmailUnique(registerDto.getEmail());
        checkUsernameUnique(registerDto.getUsername());
    }
}
