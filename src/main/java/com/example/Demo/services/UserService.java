package com.example.Demo.services;
import aj.org.objectweb.asm.commons.Remapper;
import com.example.Demo.DTO.RegisterRequest;

import com.example.Demo.Entity.User;
import com.example.Demo.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

@Service//indicateds a spring service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;    //declares final instance oparations
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LogManager.getLogger(UserService.class);


    // method to register a new user
    //@return the saved user entity
    public User register(RegisterRequest request)
    {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists!");
        }
        try {
            User user = new User();

            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());

            //set users request by each
            user.setEmail(request.getEmail());

            String encodedPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(encodedPassword);

            user.setRole(request.getRole());
            logger.info("Successfully registered a user: "+ user.getEmail());
            return userRepository.save(user);  //save the user to the data base via repository and return the saved entity

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("An Error occurred :" +e.getMessage());
        }

        return null;
    }

    public Optional<User>findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}

