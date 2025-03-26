package com.trainingmanagernew.Security.Service;

import com.trainingmanagernew.Security.Exception.SecurityCustomExceptions;
import com.trainingmanagernew.UserModule.Entity.UserEntity;
import com.trainingmanagernew.UserModule.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsCustom implements UserDetailsService {
    private final UserRepository userRepository;

        public UserDetailsCustom(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<UserEntity> user = userRepository.findByUsername(username);
            if (user.isPresent()){
                return new UserDetailsAdapter(user.get());
            }
            else throw new SecurityCustomExceptions.UserNotFoundException();
    }
}

