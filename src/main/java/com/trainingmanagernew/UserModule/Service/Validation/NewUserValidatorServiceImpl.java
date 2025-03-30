package com.trainingmanagernew.UserModule.Service.Validation;

import com.trainingmanagernew.UserModule.Dto.RegisterDto;
import com.trainingmanagernew.UserModule.Exception.UserCustomExceptions;
import com.trainingmanagernew.UserModule.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class NewUserValidatorServiceImpl implements NewUserValidationService {

    private final UserRepository userRepository;

    NewUserValidatorServiceImpl(UserRepository userRepository){
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

    @Override
    public void checkPhoneNumberUnique(String number) {
        if (userRepository.existsByNumber(number)){
            throw new UserCustomExceptions.PhoneNumberAlreadyExistsException();
        }
    }

    @Override
    public void validate(RegisterDto registerDto){
        checkEmailUnique(registerDto.getEmail());
        checkUsernameUnique(registerDto.getUsername());
        checkPhoneNumberUnique(registerDto.getNumber());
    }
}
