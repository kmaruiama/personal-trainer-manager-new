package com.trainingmanagernew.UserModule.Service.Register;

import com.trainingmanagernew.SecurityModule.Entity.Role;
import com.trainingmanagernew.SecurityModule.Exception.SecurityCustomExceptions;
import com.trainingmanagernew.SecurityModule.Repository.RoleRepository;
import com.trainingmanagernew.UserModule.Dto.RegisterDto;
import com.trainingmanagernew.UserModule.Entity.UserEntity;
import com.trainingmanagernew.UserModule.Repository.UserRepository;
import com.trainingmanagernew.UserModule.Service.Validation.NewUserValidationService;
import com.trainingmanagernew.UserModule.UserEventEmitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterNewUserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserEventEmitter userEventEmitter;
    @Mock private RoleRepository roleRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private NewUserValidationService newUserValidationService;

    @InjectMocks private RegisterNewUserService registerNewUserService;

    private RegisterDto registerDto;

    @BeforeEach
    void setup() {
        registerDto = new RegisterDto();
        registerDto.setEmail("test@example.com");
        registerDto.setUsername("testuser");
        registerDto.setPassword("securepassword");
        registerDto.setNumber("1234567890");
    }

    @Test
    void registerShouldValidateAndSaveUser(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        role.setId(1L);
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        UserEntity mockUserEntity = mock(UserEntity.class);
        when(mockUserEntity.getId()).thenReturn(UUID.randomUUID());

        when(userRepository.save(any(UserEntity.class))).thenReturn(mockUserEntity);

        registerNewUserService.register(registerDto);

        verify(newUserValidationService).validate(registerDto);
        verify(userRepository).save(any(UserEntity.class));
        verify(userEventEmitter).newUserRegistered(mockUserEntity.getId(), registerDto);
    }

    @Test
    void shouldThrowRoleNotFoundException() {
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.empty());

        assertThrows(SecurityCustomExceptions.RoleAtributionException.class, () -> {
            registerNewUserService.register(registerDto);
        });

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void classParametersShouldBeEqual(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        role.setId(1L);

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        UserEntity userEntity = invokeCreateUserEntity(registerDto);

        assertEquals(registerDto.getNumber(), userEntity.getNumber());
        assertEquals(registerDto.getEmail(), userEntity.getEmail());
        assertEquals(registerDto.getUsername(), userEntity.getUsername());
    }

    private UserEntity invokeCreateUserEntity(RegisterDto registerDto) {
        try {
            var method = RegisterNewUserService.class.getDeclaredMethod("createUserEntity", RegisterDto.class);
            method.setAccessible(true);
            return (UserEntity) method.invoke(registerNewUserService, registerDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
