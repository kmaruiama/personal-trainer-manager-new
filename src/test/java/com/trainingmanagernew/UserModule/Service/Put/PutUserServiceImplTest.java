package com.trainingmanagernew.UserModule.Service.Put;

import com.trainingmanagernew.UserModule.Dto.UserDto;
import com.trainingmanagernew.UserModule.Entity.UserEntity;
import com.trainingmanagernew.UserModule.Repository.UserRepository;
import com.trainingmanagernew.UserModule.Service.LocalJwtExtractor.UserTokenExtraction;
import com.trainingmanagernew.UserModule.Service.Validation.NewUserValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PutUserServiceImplTest {
    @Mock private UserRepository userRepository;
    @Mock private NewUserValidationService newUserValidationService;
    @Mock private UserTokenExtraction userTokenExtraction;
    @InjectMocks private PutUserServiceImpl putUserService;

    private UserDto userDto;

    @BeforeEach
    void setUp(){
        userDto = new UserDto();
        userDto.setNumber("12345654324");
        userDto.setEmail("whatever@whatever.com");
        userDto.setUsername("newUsername");
        userDto.setId(UUID.randomUUID());
    }

    @Test
    void putShouldBeValidatedAndChangeTheUserEntity(){
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setUsername("oldUsername");
        mockUserEntity.setEmail("oldWhatever@whatever.com");
        mockUserEntity.setNumber("23454234543");

        when(userTokenExtraction.extractUuid(any(String.class))).thenReturn(userDto.getId());
        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(mockUserEntity));
        putUserService.put(userDto, "aaa");

        verify(newUserValidationService).checkPhoneNumberUnique(userDto.getNumber());
        verify(newUserValidationService).checkEmailUnique(userDto.getEmail());
        verify(newUserValidationService).checkUsernameUnique(userDto.getUsername());
        assertEquals(mockUserEntity.getUsername(), userDto.getUsername());
        assertEquals(mockUserEntity.getEmail(), userDto.getEmail());
        assertEquals(mockUserEntity.getNumber(), userDto.getNumber());
    }

    @Test
    void shouldNotBeValidatedAndChangeTheUserEntity(){
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setUsername("newUsername");
        mockUserEntity.setEmail("whatever@whatever.com");
        mockUserEntity.setNumber("12345654324");

        when(userTokenExtraction.extractUuid(any(String.class))).thenReturn(userDto.getId());
        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(mockUserEntity));
        putUserService.put(userDto, "aaa");

        verify(newUserValidationService, times(0)).checkPhoneNumberUnique(userDto.getNumber());
        verify(newUserValidationService, times(0)).checkEmailUnique(userDto.getEmail());
        verify(newUserValidationService, times(0)).checkUsernameUnique(userDto.getUsername());

        assertEquals(mockUserEntity.getUsername(), userDto.getUsername());
        assertEquals(mockUserEntity.getEmail(), userDto.getEmail());
        assertEquals(mockUserEntity.getNumber(), userDto.getNumber());
    }
}