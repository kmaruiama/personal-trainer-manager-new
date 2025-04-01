package com.trainingmanagernew.UserModule.Service.Put;

import com.trainingmanagernew.UserModule.Dto.UserDto;
import com.trainingmanagernew.UserModule.Entity.UserEntity;
import com.trainingmanagernew.UserModule.Exception.UserCustomExceptions;
import com.trainingmanagernew.UserModule.Repository.UserRepository;
import com.trainingmanagernew.UserModule.Service.LocalJwtExtractor.UserTokenExtraction;
import com.trainingmanagernew.UserModule.Service.Validation.NewUserValidationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PutUserServiceImpl implements PutUserService {

    private final UserRepository userRepository;
    private final NewUserValidationService newUserValidationService;
    private final UserTokenExtraction userTokenExtraction;

    public PutUserServiceImpl(UserRepository userRepository, NewUserValidationService newUserValidationService, UserTokenExtraction userTokenExtraction) {
        this.userRepository = userRepository;
        this.newUserValidationService = newUserValidationService;
        this.userTokenExtraction = userTokenExtraction;
    }

    //@AuthorizeRequest
    /* na verdade eu não preciso desse aspecto já que o parse do id modificado vai vir justamente do
    token */
    @Transactional
    @Override
    public void put(UserDto userDto, String authHeader) {
        UUID userId = userTokenExtraction.extractUuid(authHeader);
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        UserEntity userEntity;
        if (userEntityOptional.isPresent()){
            userEntity = userEntityOptional.get();
        }
        else{
            throw new UserCustomExceptions.UserNotFoundException();
        }
        validateRequestParameters(userEntity, userDto);
        update(userEntity, userDto);
    }

    private void update(UserEntity userEntity, UserDto userDto){
        userEntity.setEmail(userDto.getEmail());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setNumber(userDto.getNumber());
    };

    private void validateRequestParameters(UserEntity userEntity, UserDto userDto){
        //se o nome não for igual ao que já estava antes, valida para checkar se outro user não está usando
        if (!userEntity.getUsername().equals(userDto.getUsername())){
            newUserValidationService.checkUsernameUnique(userDto.getUsername());
        }
        //idem
        if (!userEntity.getNumber().equals(userDto.getNumber())){
            newUserValidationService.checkPhoneNumberUnique(userDto.getNumber());
        }
        //idem
        if (!userEntity.getEmail().equals(userDto.getEmail())){
            newUserValidationService.checkEmailUnique(userDto.getEmail());
        }
    }
}





