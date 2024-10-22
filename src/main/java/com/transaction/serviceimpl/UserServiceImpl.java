package com.transaction.serviceimpl;

import com.transaction.dto.NewUserDto;
import com.transaction.entity.Users;
import com.transaction.enums.GenericStatus;
import com.transaction.pojo.UserPojo;
import com.transaction.repository.UserRepository;
import com.transaction.repository.app.AppRepository;
import com.transaction.security.JwtService;
import com.transaction.security.PasswordService;
import com.transaction.services.UserService;
import com.transaction.utils.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository usersRepository;
    private final PasswordService passwordService;
    private final AppRepository appRepository;
    private final JwtService jwtService;

    @Override
    public UserPojo createUser(NewUserDto newUserDto) {
        if (usersRepository.findByEmail(newUserDto.getEmail()).isPresent()) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST,"User with email " + newUserDto.getEmail() + " already exists.");
        }
        String randomPassword = generateRandomPassword();
        Users newUser = Users.builder()
                .username(newUserDto.getEmail())
                .email(newUserDto.getEmail())
                .firstName(newUserDto.getFirstName())
                .lastName(newUserDto.getLastName())
                .displayName(newUserDto.getFirstName() + " " + newUserDto.getLastName())
                .password(passwordService.hashPassword(randomPassword))
                .build();

        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setStatus(GenericStatus.ACTIVE);

        Users savedUser = appRepository.persist(newUser);

        return new UserPojo(
                savedUser.getFirstName(),
                savedUser.getLastName(),
                newUserDto.getPhoneNumber(),
                savedUser.getEmail(),
                jwtService.generateJwtToken(savedUser.getId())
        );
    }


    private String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(12);
    }
}
