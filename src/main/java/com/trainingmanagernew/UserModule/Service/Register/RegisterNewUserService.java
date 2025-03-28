package com.trainingmanagernew.UserModule.Service.Register;

import com.trainingmanagernew.SecurityModule.Entity.Role;
import com.trainingmanagernew.SecurityModule.Exception.SecurityCustomExceptions;
import com.trainingmanagernew.SecurityModule.Repository.RoleRepository;
import com.trainingmanagernew.UserModule.Dto.RegisterDto;
import com.trainingmanagernew.UserModule.Entity.UserEntity;
import com.trainingmanagernew.UserModule.Repository.UserRepository;
import com.trainingmanagernew.UserModule.UserEventEmitter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class RegisterNewUserService {

    private final NewUserValidatorService newUserValidatorService;
    private final UserRepository userRepository;
    private final UserEventEmitter userEventEmitter;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    Logger LOGGER = Logger.getLogger(RegisterNewUserService.class.getName());

    RegisterNewUserService(NewUserValidatorService newUserValidatorService,
                           UserRepository userRepository,
                           UserEventEmitter userEventEmitter,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.newUserValidatorService = newUserValidatorService;
        this.userRepository = userRepository;
        this.userEventEmitter = userEventEmitter;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterDto registerDto){
        newUserValidatorService.validate(registerDto);
        UserEntity userEntity = createUserEntity(registerDto);
        userEventEmitter.newUserRegistered(userEntity.getId(), registerDto);
    }

    private UserEntity createUserEntity(RegisterDto registerDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userEntity.setUsername(registerDto.getUsername());

        setRoles(userEntity);

        userRepository.save(userEntity);
        LOGGER.info("NOVO USUÁRIO " + userEntity.getUsername() + " REGISTRADO NO BANCO DE DADOS");
        return userEntity;
    }

    private void setRoles(UserEntity userEntity){
        List<Role> roleList = new ArrayList<>();
        Optional<Role> role = roleRepository.findByName("ROLE_ADMIN");
        if (role.isEmpty()){
            throw new SecurityCustomExceptions.RoleAtributionException();
        }
        roleList.add(role.get());
        userEntity.setRoles(roleList);
    }

}
